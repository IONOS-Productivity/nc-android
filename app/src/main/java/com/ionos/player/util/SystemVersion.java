/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.util;

import android.os.Build;

import androidx.annotation.ChecksSdkIntAtLeast;

public class SystemVersion {

    /**
     * Checks if run on Android 11 Red Velvet Cake (30) or newer
     */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    public static boolean greaterOrEqualToR() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    /**
     * Checks if run on Android 12 Snow Cone (31) or newer
     */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    public static boolean greaterOrEqualToS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    /**
     * Checks if run on Android 13 Tiramisu (33) or newer
     */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    public static boolean greaterOrEqualToTiramisu() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

}
