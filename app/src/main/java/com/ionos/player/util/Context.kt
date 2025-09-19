/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.util

import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import androidx.annotation.ChecksSdkIntAtLeast

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun Context.isPictureInPictureAllowed(): Boolean {
    if (isPictureInPictureSupported()) {
        val appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
        appOpsManager?.let {
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_PICTURE_IN_PICTURE, Process.myUid(), packageName)
            } else {
                @Suppress("DEPRECATION")
                it.checkOpNoThrow(AppOpsManager.OPSTR_PICTURE_IN_PICTURE, Process.myUid(), packageName)
            }
            return mode == AppOpsManager.MODE_ALLOWED
        }
    }
    return false
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
private fun Context.isPictureInPictureSupported(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
}
