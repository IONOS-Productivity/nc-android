/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.utils

import com.nextcloud.utils.BuildHelper
import com.owncloud.android.BuildConfig

object IonosBuildHelper {

    @JvmStatic
    fun isIonosFlavor(): Boolean {
        return BuildHelper.GPLAY == BuildConfig.FLAVOR
    }
}
