/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.owncloud.android.utils.theme

import com.ionos.annotation.IonosCustomization
import com.nextcloud.android.common.ui.theme.utils.AndroidViewThemeUtils
import com.nextcloud.android.common.ui.theme.utils.MaterialViewThemeUtils

@IonosCustomization
class IonosViewThemeUtils(
    private val platformUtil: AndroidViewThemeUtils,
    private val materialUtil: MaterialViewThemeUtils,
) {

    @JvmField
    val platform = IonosAndroidViewThemeUtils(platformUtil)

    @JvmField
    val material = IonosMaterialViewThemeUtils()

    @JvmField
    val dialog = IonosDialogViewThemeUtils()
}