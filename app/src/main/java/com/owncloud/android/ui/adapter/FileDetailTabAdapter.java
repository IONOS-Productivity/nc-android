/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2018 Andy Scherzinger <info@andy-scherzinger.de>
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android.ui.adapter;

import com.nextcloud.client.account.User;
import com.nextcloud.ui.ImageDetailFragment;
import com.owncloud.android.datamodel.OCFile;
import com.owncloud.android.ui.fragment.FileDetailActivitiesFragment;
import com.owncloud.android.ui.fragment.FileDetailSharingFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * File details pager adapter.
 */
public class FileDetailTabAdapter extends FragmentStateAdapter {
    private final OCFile file;
    private final User user;
    private final boolean showSharingTab;

    private FileDetailSharingFragment fileDetailSharingFragment;
    private FileDetailActivitiesFragment fileDetailActivitiesFragment;
    private ImageDetailFragment imageDetailFragment;

    public FileDetailTabAdapter(FragmentActivity fragmentActivity,
                                OCFile file,
                                User user,
                                boolean showSharingTab) {
        super(fragmentActivity);
        this.file = file;
        this.user = user;
        this.showSharingTab = showSharingTab;
    }

    public FileDetailSharingFragment getFileDetailSharingFragment() {
        return fileDetailSharingFragment;
    }

    public FileDetailActivitiesFragment getFileDetailActivitiesFragment() {
        return fileDetailActivitiesFragment;
    }

    public ImageDetailFragment getImageDetailFragment() {
        return imageDetailFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Start: Hide tabs in IONOS //
//        return switch (position) {
//            default -> {
//                fileDetailActivitiesFragment = FileDetailActivitiesFragment.newInstance(file, user);
//                yield fileDetailActivitiesFragment;
//            }
//            case 1 -> {
//                fileDetailSharingFragment = FileDetailSharingFragment.newInstance(file, user);
//                yield fileDetailSharingFragment;
//            }
//            case 2 -> {
//                imageDetailFragment = ImageDetailFragment.newInstance(file, user);
//                yield imageDetailFragment;
//            }
//        };
        fileDetailSharingFragment = FileDetailSharingFragment.newInstance(file, user);
        return fileDetailSharingFragment;
        // End: Hide tabs in IONOS //
    }

    @Override
    public int getItemCount() {
        // Start: Hide tabs in IONOS //
//        if (showSharingTab) {
//            if (MimeTypeUtil.isImage(file)) {
//                return 3;
//            }
//            return 2;
//        } else {
//            if (MimeTypeUtil.isImage(file)) {
//                return 2;
//            }
//            return 1;
//        }
        if (showSharingTab) return 1;
        else return 0;
        // End: Hide tabs in IONOS //
    }
}
