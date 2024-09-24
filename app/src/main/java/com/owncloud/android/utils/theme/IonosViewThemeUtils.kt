/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Your Name <your@email.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
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
}