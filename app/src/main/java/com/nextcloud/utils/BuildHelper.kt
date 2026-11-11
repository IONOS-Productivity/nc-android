/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Tobias Kaminsky <tobias.kaminsky@nextcloud.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.nextcloud.utils

import com.owncloud.android.BuildConfig

object BuildHelper {
    const val GPLAY: String = "gplay"
    const val HIDRIVE_NEXT: String = "hidriveNext"

    private val GPLAY_FLAVOURS = setOf(GPLAY, HIDRIVE_NEXT)

    fun isFlavourGPlay(): Boolean = BuildConfig.FLAVOR in GPLAY_FLAVOURS

    fun isHuaweiFlavor(): Boolean = BuildConfig.FLAVOR == "huawei"
}
