/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2020 Chris Narkiewicz <hello@ezaquarii.com>
 * SPDX-FileCopyrightText: 2017 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2017 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android.ui.fragment.contactsbackup;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nextcloud.client.account.User;
import com.nextcloud.client.account.UserAccountManager;
import com.nextcloud.client.di.Injectable;
import com.nextcloud.client.files.DownloadRequest;
import com.nextcloud.client.files.Request;
import com.nextcloud.client.jobs.BackgroundJobManager;
import com.nextcloud.client.jobs.transfer.Transfer;
import com.nextcloud.client.jobs.transfer.TransferManagerConnection;
import com.nextcloud.client.jobs.transfer.TransferState;
import com.nextcloud.client.network.ClientFactory;
import com.nextcloud.utils.extensions.BundleExtensionsKt;
import com.nextcloud.utils.extensions.IntExtensionsKt;
import com.owncloud.android.R;
import com.owncloud.android.databinding.BackuplistFragmentBinding;
import com.owncloud.android.datamodel.OCFile;
import com.owncloud.android.lib.common.utils.Log_OC;
import com.owncloud.android.ui.activity.ContactsPreferenceActivity;
import com.owncloud.android.ui.asynctasks.LoadContactsTask;
import com.owncloud.android.ui.events.VCardToggleEvent;
import com.owncloud.android.ui.fragment.FileFragment;
import com.owncloud.android.utils.MimeTypeUtil;
import com.owncloud.android.utils.PermissionUtil;
import com.owncloud.android.utils.theme.ViewThemeUtils;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import ezvcard.VCard;
import kotlin.Unit;

/**
 * This fragment shows all contacts or calendars from files and allows to import them.
 */
public class BackupListFragment extends FileFragment implements Injectable {
    public static final String TAG = BackupListFragment.class.getSimpleName();

    public static final String FILE_NAMES = "FILE_NAMES";
    public static final String FILE_NAME = "FILE_NAME";
    public static final String USER = "USER";
    public static final String CHECKED_CALENDAR_ITEMS_ARRAY_KEY = "CALENDAR_CHECKED_ITEMS";
    public static final String CHECKED_CONTACTS_ITEMS_ARRAY_KEY = "CONTACTS_CHECKED_ITEMS";

    private BackuplistFragmentBinding binding;

    private BackupListAdapter listAdapter;
    private final List<VCard> vCards = new ArrayList<>();
    private final List<OCFile> ocFiles = new ArrayList<>();
    @Inject UserAccountManager accountManager;
    @Inject ClientFactory clientFactory;
    @Inject BackgroundJobManager backgroundJobManager;
    @Inject ViewThemeUtils viewThemeUtils;
    private TransferManagerConnection fileDownloader;
    private LoadContactsTask loadContactsTask = null;
    private ContactsAccount selectedAccount;

    public static BackupListFragment newInstance(OCFile file, User user) {
        BackupListFragment frag = new BackupListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(FILE_NAME, file);
        arguments.putParcelable(USER, user);
        frag.setArguments(arguments);

        return frag;
    }

    public static BackupListFragment newInstance(OCFile[] files, User user) {
        BackupListFragment frag = new BackupListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArray(FILE_NAMES, files);
        arguments.putParcelable(USER, user);
        frag.setArguments(arguments);

        return frag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_list, menu);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = BackuplistFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setHasOptionsMenu(true);

        ContactsPreferenceActivity contactsPreferenceActivity = (ContactsPreferenceActivity) getActivity();

        if (contactsPreferenceActivity != null) {
            ActionBar actionBar = contactsPreferenceActivity.getSupportActionBar();
            if (actionBar != null) {
                viewThemeUtils.files.themeActionBar(requireContext(), actionBar, R.string.actionbar_calendar_contacts_restore);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            contactsPreferenceActivity.setDrawerIndicatorEnabled(false);
        }

        if (savedInstanceState == null) {
            listAdapter = new BackupListAdapter(accountManager,
                                                clientFactory,
                                                new HashSet<>(),
                                                new HashMap<>(),
                                                this,
                                                requireContext(),
                                                viewThemeUtils);
        } else {
            HashMap<String, Integer> checkedCalendarItems = new HashMap<>();
            String[] checkedCalendarItemsArray = savedInstanceState.getStringArray(CHECKED_CALENDAR_ITEMS_ARRAY_KEY);
            if (checkedCalendarItemsArray != null) {
                for (String checkedItem : checkedCalendarItemsArray) {
                    checkedCalendarItems.put(checkedItem, -1);
                }
            }
            if (checkedCalendarItems.size() > 0) {
                showRestoreButton(true);
            }

            HashSet<Integer> checkedContactsItems = new HashSet<>();
            int[] checkedContactsItemsArray = savedInstanceState.getIntArray(CHECKED_CONTACTS_ITEMS_ARRAY_KEY);
            if (checkedContactsItemsArray != null) {
                for (int checkedItem : checkedContactsItemsArray) {
                    checkedContactsItems.add(checkedItem);
                }
            }
            if (checkedContactsItems.size() > 0) {
                showRestoreButton(true);
            }

            listAdapter = new BackupListAdapter(accountManager,
                                                clientFactory,
                                                checkedContactsItems,
                                                checkedCalendarItems,
                                                this,
                                                requireContext(),
                                                viewThemeUtils);
        }

        binding.list.setAdapter(listAdapter);
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle arguments = getArguments();
        if (arguments == null) {
            return view;
        }

        if (BundleExtensionsKt.getParcelableArgument(arguments, FILE_NAME, OCFile.class) != null) {
            ocFiles.add(BundleExtensionsKt.getParcelableArgument(arguments, FILE_NAME, OCFile.class));
        } else if (arguments.getParcelableArray(FILE_NAMES) != null) {
            for (Parcelable file : arguments.getParcelableArray(FILE_NAMES)) {
                ocFiles.add((OCFile) file);
            }
        } else {
            return view;
        }

        User user = BundleExtensionsKt.getParcelableArgument(getArguments(), USER, User.class);
        fileDownloader = new TransferManagerConnection(getActivity(), user);
        fileDownloader.registerTransferListener(this::onDownloadUpdate);
        fileDownloader.bind();

        for (OCFile file : ocFiles) {
            if (!file.isDown()) {
                Request request = new DownloadRequest(user, file);
                fileDownloader.enqueue(request);
            }

            if (MimeTypeUtil.isVCard(file) && file.isDown()) {
                setFile(file);
                loadContactsTask = new LoadContactsTask(this, file);
                loadContactsTask.execute();
            }

            if (MimeTypeUtil.isCalendar(file) && file.isDown()) {
                showLoadingMessage(false);
                listAdapter.addCalendar(file);
            }
        }

        binding.restoreSelected.setOnClickListener(v -> {
            if (checkAndAskForCalendarWritePermission()) {
                importCalendar();
            }

            if (listAdapter.getCheckedContactsIntArray().length > 0 && checkAndAskForContactsWritePermission()) {
                importContacts(selectedAccount);
                return;
            }

            Snackbar
                .make(
                    binding.list,
                    R.string.contacts_preferences_import_scheduled,
                    Snackbar.LENGTH_LONG
                     )
                .show();

            closeFragment();
        });

        viewThemeUtils.material.colorMaterialButtonPrimaryBorderless(binding.restoreSelected);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (fileDownloader != null) {
            fileDownloader.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(CHECKED_CALENDAR_ITEMS_ARRAY_KEY, listAdapter.getCheckedCalendarStringArray());
        outState.putIntArray(CHECKED_CONTACTS_ITEMS_ARRAY_KEY, listAdapter.getCheckedContactsIntArray());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(VCardToggleEvent event) {
        if (event.getShowRestoreButton()) {
            binding.contactlistRestoreSelectedContainer.setVisibility(View.VISIBLE);
        } else {
            binding.contactlistRestoreSelectedContainer.setVisibility(View.GONE);
        }
    }

    public void showRestoreButton(boolean show) {
        binding.contactlistRestoreSelectedContainer.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ContactsPreferenceActivity contactsPreferenceActivity = (ContactsPreferenceActivity) getActivity();
        contactsPreferenceActivity.setDrawerIndicatorEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onResume() {
        super.onResume();
        ContactsPreferenceActivity contactsPreferenceActivity = (ContactsPreferenceActivity) getActivity();
        contactsPreferenceActivity.setDrawerIndicatorEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        if (loadContactsTask != null) {
            loadContactsTask.cancel(true);
        }
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean retval;
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            closeFragment();
            retval = true;
        } else if (itemId == R.id.action_select_all) {
            item.setChecked(!item.isChecked());
            setSelectAllMenuItem(item, item.isChecked());
            listAdapter.selectAll(item.isChecked());
            retval = true;
        } else {
            retval = super.onOptionsItemSelected(item);
        }

        return retval;
    }

    public void showLoadingMessage(boolean showIt) {
        binding.loadingListContainer.setVisibility(showIt ? View.VISIBLE : View.GONE);
    }

    private void setSelectAllMenuItem(MenuItem selectAll, boolean checked) {
        selectAll.setChecked(checked);
        if (checked) {
            selectAll.setIcon(R.drawable.ic_select_none);
        } else {
            selectAll.setIcon(R.drawable.ic_select_all);
        }
    }

    private void importContacts(ContactsAccount account) {
        final var selectedContractsFilePath = writeCheckedContractsInCacheDir();
        if (selectedContractsFilePath == null) {
            Snackbar.make(binding.list, R.string.contacts_preferences_import_scheduled_fail, Snackbar.LENGTH_LONG).show();
            return;
        }

        String name = null;
        String type = null;
        if (account != null) {
            name = account.getName();
            type = account.getType();
        }

        backgroundJobManager.startImmediateContactsImport(name,
                                                          type,
                                                          getFile().getStoragePath(),
                                                          selectedContractsFilePath);

        Snackbar.make(binding.list, R.string.contacts_preferences_import_scheduled, Snackbar.LENGTH_LONG).show();
        closeFragment();
    }


    /**
     * Writes a HashSet of integers to a temporary file in the app's cache directory.
     * The file stores the data as a comma-separated string. This is necessary because
     * WorkManager cannot handle large data directly due to its size limit.
     *
     * @return the absolute file path of the temporary cache file
     */
    private String writeCheckedContractsInCacheDir() {
        try {
            final var filename = "selectedContacts-" + System.currentTimeMillis();
            File file = new File(requireContext().getCacheDir(), filename + ".txt");

            final var contracts = listAdapter.getCheckedContactsIntArray();
            final var contractsAsByteArray = IntExtensionsKt.toByteArray(contracts);

            FileUtils.writeByteArrayToFile(file, contractsAsByteArray);

            return file.getAbsolutePath();
        } catch (Exception e) {
            Log_OC.e(TAG, "Exception writeCheckedContractsInCacheDir: " + e);
            return null;
        }
    }

    private void importCalendar() {
        backgroundJobManager.startImmediateCalendarImport(listAdapter.getCheckedCalendarPathsArray());

        Snackbar
            .make(
                binding.list,
                R.string.contacts_preferences_import_scheduled,
                Snackbar.LENGTH_LONG
                 )
            .show();

        closeFragment();
    }

    private void closeFragment() {
        ContactsPreferenceActivity contactsPreferenceActivity = (ContactsPreferenceActivity) getActivity();
        if (contactsPreferenceActivity != null) {
            contactsPreferenceActivity.onBackPressed();
        }
    }

    private boolean checkAndAskForContactsWritePermission() {
        // check permissions
        if (!PermissionUtil.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                               PermissionUtil.PERMISSIONS_WRITE_CONTACTS);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndAskForCalendarWritePermission() {
        // check permissions
        if (!PermissionUtil.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR},
                               PermissionUtil.PERMISSIONS_WRITE_CALENDAR);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionUtil.PERMISSIONS_WRITE_CONTACTS) {
            for (int index = 0; index < permissions.length; index++) {
                if (Manifest.permission.WRITE_CONTACTS.equalsIgnoreCase(permissions[index])) {
                    if (grantResults[index] >= 0) {
                        importContacts(selectedAccount);
                    } else {
                        if (getView() != null) {
                            Snackbar.make(getView(), R.string.contactlist_no_permission, Snackbar.LENGTH_LONG)
                                .show();
                        } else {
                            Toast.makeText(getContext(), R.string.contactlist_no_permission, Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                }
            }
        }

        if (requestCode == PermissionUtil.PERMISSIONS_WRITE_CALENDAR) {
            for (int index = 0; index < permissions.length; index++) {
                if (Manifest.permission.WRITE_CALENDAR.equalsIgnoreCase(permissions[index])) {
                    if (grantResults[index] >= 0) {
                        importContacts(selectedAccount);
                    } else {
                        if (getView() != null) {
                            Snackbar.make(getView(), R.string.contactlist_no_permission, Snackbar.LENGTH_LONG)
                                .show();
                        } else {
                            Toast.makeText(getContext(), R.string.contactlist_no_permission, Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                }
            }
        }
    }

    private Unit onDownloadUpdate(Transfer download) {
        final Activity activity = getActivity();
        if (download.getState() == TransferState.COMPLETED && activity != null) {
            OCFile ocFile = download.getFile();

            if (MimeTypeUtil.isVCard(ocFile)) {
                setFile(ocFile);
                loadContactsTask = new LoadContactsTask(this, ocFile);
                loadContactsTask.execute();
            }
        }
        return Unit.INSTANCE;
    }

    public void loadVCards(List<VCard> cards) {
        showLoadingMessage(false);
        vCards.clear();
        vCards.addAll(cards);
        listAdapter.replaceVcards(vCards);
    }

    public static String getDisplayName(VCard vCard) {
        if (vCard.getFormattedName() != null) {
            return vCard.getFormattedName().getValue();
        } else if (vCard.getTelephoneNumbers() != null && vCard.getTelephoneNumbers().size() > 0) {
            return vCard.getTelephoneNumbers().get(0).getText();
        } else if (vCard.getEmails() != null && vCard.getEmails().size() > 0) {
            return vCard.getEmails().get(0).getValue();
        }

        return "";
    }

    public boolean hasCalendarEntry() {
        return listAdapter.hasCalendarEntry();
    }

    public void setSelectedAccount(ContactsAccount account) {
        selectedAccount = account;
    }
}
