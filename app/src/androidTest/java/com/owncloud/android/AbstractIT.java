/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2018 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.internal.TestNameDetector;
import com.nextcloud.client.account.User;
import com.nextcloud.client.account.UserAccountManager;
import com.nextcloud.client.account.UserAccountManagerImpl;
import com.nextcloud.client.device.BatteryStatus;
import com.nextcloud.client.device.PowerManagementService;
import com.nextcloud.client.jobs.upload.FileUploadWorker;
import com.nextcloud.client.network.Connectivity;
import com.nextcloud.client.network.ConnectivityService;
import com.nextcloud.client.preferences.AppPreferencesImpl;
import com.nextcloud.client.preferences.DarkMode;
import com.nextcloud.common.NextcloudClient;
import com.nextcloud.test.GrantStoragePermissionRule;
import com.nextcloud.test.RandomStringGenerator;
import com.owncloud.android.datamodel.ArbitraryDataProvider;
import com.owncloud.android.datamodel.ArbitraryDataProviderImpl;
import com.owncloud.android.datamodel.FileDataStorageManager;
import com.owncloud.android.datamodel.OCFile;
import com.owncloud.android.datamodel.UploadsStorageManager;
import com.owncloud.android.db.OCUpload;
import com.owncloud.android.files.services.NameCollisionPolicy;
import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.accounts.AccountUtils;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.files.ExistenceCheckRemoteOperation;
import com.owncloud.android.lib.resources.status.CapabilityBooleanType;
import com.owncloud.android.lib.resources.status.GetCapabilitiesRemoteOperation;
import com.owncloud.android.lib.resources.status.OCCapability;
import com.owncloud.android.lib.resources.status.OwnCloudVersion;
import com.owncloud.android.operations.CreateFolderOperation;
import com.owncloud.android.operations.UploadFileOperation;
import com.owncloud.android.utils.FileStorageUtils;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.owncloud.android.lib.common.accounts.AccountUtils.Constants.KEY_USER_ID;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * Common base for all integration tests.
 */
public abstract class AbstractIT {
    @Rule
    public final TestRule permissionRule = GrantStoragePermissionRule.grant();

    protected static OwnCloudClient client;
    protected static NextcloudClient nextcloudClient;
    protected static Account account;
    protected static User user;
    protected static Context targetContext;
    protected static String DARK_MODE = "";
    protected static String COLOR = "";

    protected Activity currentActivity;

    protected FileDataStorageManager fileDataStorageManager =
        new FileDataStorageManager(user, targetContext.getContentResolver());

    protected ArbitraryDataProvider arbitraryDataProvider = new ArbitraryDataProviderImpl(targetContext);

    @BeforeClass
    public static void beforeAll() {
        try {
            // clean up
            targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            AccountManager platformAccountManager = AccountManager.get(targetContext);

            for (Account account : platformAccountManager.getAccounts()) {
                if (account.type.equalsIgnoreCase("nextcloud")) {
                    platformAccountManager.removeAccountExplicitly(account);
                }
            }

            account = createAccount("test@https://nextcloud.localhost");
            user = getUser(account);

            client = OwnCloudClientFactory.createOwnCloudClient(account, targetContext);
            nextcloudClient = OwnCloudClientFactory.createNextcloudClient(user, targetContext);
        } catch (OperationCanceledException |
                 IOException |
                 AccountUtils.AccountNotFoundException |
                 AuthenticatorException e) {
            throw new RuntimeException("Error setting up clients", e);
        }

        Bundle arguments = androidx.test.platform.app.InstrumentationRegistry.getArguments();

        // color
        String colorParameter = arguments.getString("COLOR");
        if (!TextUtils.isEmpty(colorParameter)) {
            FileDataStorageManager fileDataStorageManager = new FileDataStorageManager(user,
                                                                                       targetContext.getContentResolver());

            String colorHex = null;
            COLOR = colorParameter;
            switch (colorParameter) {
                case "red":
                    colorHex = "#7c0000";
                    break;

                case "green":
                    colorHex = "#00ff00";
                    break;

                case "white":
                    colorHex = "#ffffff";
                    break;

                case "black":
                    colorHex = "#000000";
                    break;

                case "lightgreen":
                    colorHex = "#aaff00";
                    break;

                default:
                    break;
            }

            OCCapability capability = fileDataStorageManager.getCapability(account.name);
            capability.setGroupfolders(CapabilityBooleanType.TRUE);

            if (colorHex != null) {
                capability.setServerColor(colorHex);
            }

            fileDataStorageManager.saveCapabilities(capability);
        }

        // dark / light
        String darkModeParameter = arguments.getString("DARKMODE");

        if (darkModeParameter != null) {
            if ("dark".equalsIgnoreCase(darkModeParameter)) {
                DARK_MODE = "dark";
                AppPreferencesImpl.fromContext(targetContext).setDarkThemeMode(DarkMode.DARK);
                MainApp.setAppTheme(DarkMode.DARK);
            } else {
                DARK_MODE = "light";
            }
        }

        if ("light".equalsIgnoreCase(DARK_MODE) && "blue".equalsIgnoreCase(COLOR)) {
            // use already existing names
            DARK_MODE = "";
            COLOR = "";
        }
    }

    protected void testOnlyOnServer(OwnCloudVersion version) throws AccountUtils.AccountNotFoundException {
        OCCapability ocCapability = getCapability();
        assumeTrue(ocCapability.getVersion().isNewerOrEqual(version));
    }

    protected OCCapability getCapability() throws AccountUtils.AccountNotFoundException {
        NextcloudClient client = OwnCloudClientFactory.createNextcloudClient(user, targetContext);

        OCCapability ocCapability = (OCCapability) new GetCapabilitiesRemoteOperation()
            .execute(client)
            .getSingleData();

        return ocCapability;
    }

    @Before
    public void enableAccessibilityChecks() {
        androidx.test.espresso.accessibility.AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @After
    public void after() {
        fileDataStorageManager.removeLocalFiles(user, fileDataStorageManager);
        fileDataStorageManager.deleteAllFiles();
    }

    protected FileDataStorageManager getStorageManager() {
        return fileDataStorageManager;
    }

    protected Account[] getAllAccounts() {
        return AccountManager.get(targetContext).getAccounts();
    }

    protected static void createDummyFiles() throws IOException {
        File tempPath = new File(FileStorageUtils.getTemporalPath(account.name));
        if (!tempPath.exists()) {
            assertTrue(tempPath.mkdirs());
        }

        assertTrue(tempPath.exists());

        createFile("empty.txt", 0);
        createFile("nonEmpty.txt", 100);
        createFile("chunkedFile.txt", 500000);
    }

    protected static File getDummyFile(String name) throws IOException {
        File file = new File(FileStorageUtils.getInternalTemporalPath(account.name, targetContext) + File.separator + name);

        if (file.exists()) {
            return file;
        } else if (name.endsWith("/")) {
            file.mkdirs();
            return file;
        } else {
            return switch (name) {
                case "empty.txt" -> createFile("empty.txt", 0);
                case "nonEmpty.txt" -> createFile("nonEmpty.txt", 100);
                case "chunkedFile.txt" -> createFile("chunkedFile.txt", 500000);
                default -> createFile(name, 0);
            };
        }
    }

    public static File createFile(String name, int iteration) throws IOException {
        File file = new File(FileStorageUtils.getTemporalPath(account.name) + File.separator + name);
        if (!file.getParentFile().exists()) {
            assertTrue(file.getParentFile().mkdirs());
        }

        file.createNewFile();

        FileWriter writer = new FileWriter(file);

        for (int i = 0; i < iteration; i++) {
            writer.write("123123123123123123123123123\n");
        }
        writer.flush();
        writer.close();

        return file;
    }

    protected File getFile(String filename) throws IOException {
        InputStream inputStream = getInstrumentation().getContext().getAssets().open(filename);
        File temp = File.createTempFile("file", "file");
        FileUtils.copyInputStreamToFile(inputStream, temp);

        return temp;
    }

    protected void waitForIdleSync() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    protected void onIdleSync(Runnable recipient) {
        InstrumentationRegistry.getInstrumentation().waitForIdle(recipient);
    }

    protected void openDrawer(IntentsTestRule activityRule) {
        Activity sut = activityRule.launchActivity(null);

        shortSleep();

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        waitForIdleSync();

        screenshot(sut);
    }

    protected Activity getCurrentActivity() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry
                .getInstance()
                .getActivitiesInStage(Stage.RESUMED);

            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next();
            }
        });

        return currentActivity;
    }

    protected static void shortSleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void longSleep() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void sleep(int second) {
        try {
            Thread.sleep(1000L * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public OCFile createFolder(String remotePath) {
        RemoteOperationResult check = new ExistenceCheckRemoteOperation(remotePath, false).execute(client);

        if (!check.isSuccess()) {
            assertTrue(new CreateFolderOperation(remotePath, user, targetContext, getStorageManager())
                           .execute(client)
                           .isSuccess());
        }

        return getStorageManager().getFileByDecryptedRemotePath(remotePath.endsWith("/") ? remotePath : remotePath + "/");
    }

    public void uploadFile(File file, String remotePath) {
        OCUpload ocUpload = new OCUpload(file.getAbsolutePath(), remotePath, account.name);

        uploadOCUpload(ocUpload);
    }

    public void uploadOCUpload(OCUpload ocUpload) {
        ConnectivityService connectivityServiceMock = new ConnectivityService() {
            @Override
            public void isNetworkAndServerAvailable(@NonNull GenericCallback<Boolean> callback) {

            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public boolean isInternetWalled() {
                return false;
            }

            @Override
            public Connectivity getConnectivity() {
                return Connectivity.CONNECTED_WIFI;
            }
        };

        PowerManagementService powerManagementServiceMock = new PowerManagementService() {
            @NonNull
            @Override
            public BatteryStatus getBattery() {
                return new BatteryStatus();
            }

            @Override
            public boolean isPowerSavingEnabled() {
                return false;
            }

            @Override
            public boolean isPowerSavingExclusionAvailable() {
                return false;
            }
        };

        UserAccountManager accountManager = UserAccountManagerImpl.fromContext(targetContext);
        UploadsStorageManager uploadsStorageManager = new UploadsStorageManager(accountManager,
                                                                                targetContext.getContentResolver());

        UploadFileOperation newUpload = new UploadFileOperation(
            uploadsStorageManager,
            connectivityServiceMock,
            powerManagementServiceMock,
            user,
            null,
            ocUpload,
            NameCollisionPolicy.DEFAULT,
            FileUploadWorker.LOCAL_BEHAVIOUR_COPY,
            targetContext,
            false,
            false,
            getStorageManager()
        );
        newUpload.addRenameUploadListener(() -> {
            // dummy
        });

        newUpload.setRemoteFolderToBeCreated();

        RemoteOperationResult result = newUpload.execute(client);
        assertTrue(result.getLogMessage(), result.isSuccess());
    }

    protected void enableRTL() {
        Locale locale = new Locale("ar");
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, null);
    }

    protected void resetLocale() {
        Locale locale = new Locale("en");
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, null);
    }

    protected void screenshot(View view) {
        screenshot(view, "");
    }

    protected void screenshotViaName(Activity activity, String name) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Screenshot.snapActivity(activity).setName(name).record();
        }
    }

    protected void screenshotViaName(View view, String name) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Screenshot.snap(view).setName(name).record();
        }
    }

    protected void screenshot(View view, String prefix) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Screenshot.snap(view).setName(createName(prefix)).record();
        }
    }

    protected void screenshot(Activity sut) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Screenshot.snapActivity(sut).setName(createName()).record();
        }
    }

    protected void screenshot(DialogFragment dialogFragment, String prefix) {
        screenshot(Objects.requireNonNull(dialogFragment.requireDialog().getWindow()).getDecorView(), prefix);
    }

    private String createName() {
        return createName("");
    }

    public String createName(String name, String prefix) {
        if (!TextUtils.isEmpty(prefix)) {
            name = name + "_" + prefix;
        }

        if (!DARK_MODE.isEmpty()) {
            name = name + "_" + DARK_MODE;
        }

        if (!COLOR.isEmpty()) {
            name = name + "_" + COLOR;
        }

        return name;
    }

    private String createName(String prefix) {
        String name = TestNameDetector.getTestClass() + "_" + TestNameDetector.getTestName();
        return createName(name, prefix);
    }

    public static String getUserId(User user) {
        return AccountManager.get(targetContext).getUserData(user.toPlatformAccount(), KEY_USER_ID);
    }

    public String getRandomName() {
        return getRandomName(5);
    }

    public String getRandomName(int length) {
        return RandomStringGenerator.make(length);
    }

    protected static User getUser(Account account) {
        Optional<User> optionalUser = UserAccountManagerImpl.fromContext(targetContext).getUser(account.name);
        return optionalUser.orElseThrow(IllegalAccessError::new);
    }

    protected static Account createAccount(String name) {
        AccountManager platformAccountManager = AccountManager.get(targetContext);

        Account temp = new Account(name, MainApp.getAccountType(targetContext));
        int atPos = name.lastIndexOf('@');
        platformAccountManager.addAccountExplicitly(temp, "password", null);
        platformAccountManager.setUserData(temp, AccountUtils.Constants.KEY_OC_BASE_URL,
                                           name.substring(atPos + 1));
        platformAccountManager.setUserData(temp, KEY_USER_ID, name.substring(0, atPos));

        Account account = UserAccountManagerImpl.fromContext(targetContext).getAccountByName(name);
        if (account == null) {
            throw new ActivityNotFoundException();
        }
        return account;
    }

    protected static boolean removeAccount(Account account) {
        return AccountManager.get(targetContext).removeAccountExplicitly(account);
    }
}
