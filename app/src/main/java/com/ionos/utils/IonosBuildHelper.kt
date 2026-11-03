/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.utils

import com.owncloud.android.BuildConfig

object IonosBuildHelper {

    private const val IONOS_APPLICATION_ID = "com.ionos.hidrivenext"
    private const val HIDRIVE_NEXT_APPLICATION_ID = "com.strato.hidrivenext"

    private val BRANDED_APPLICATION_IDS = setOf(
        IONOS_APPLICATION_ID,
        HIDRIVE_NEXT_APPLICATION_ID,
    )

    /** True for every white-label HiDrive Next brand, not only the IONOS one. */
    @JvmStatic
    fun isIonosBuild(): Boolean {
        return BuildConfig.APPLICATION_ID in BRANDED_APPLICATION_IDS
    }
}
