/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-FileCopyrightText: 2022 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android.utils.theme

import com.ionos.annotation.IonosCustomization
import com.ionos.utils.IonosAndroidViewThemeUtils
import com.ionos.utils.IonosAndroidXViewThemeUtils
import com.ionos.utils.IonosDialogViewThemeUtils
import com.ionos.utils.IonosFilesSpecificViewThemeUtils
import com.ionos.utils.IonosMaterialViewThemeUtils
import com.nextcloud.android.common.ui.color.ColorUtil
import com.nextcloud.android.common.ui.theme.MaterialSchemes
import com.nextcloud.android.common.ui.theme.ViewThemeUtilsBase
import com.nextcloud.android.common.ui.theme.utils.AndroidViewThemeUtils
import com.nextcloud.android.common.ui.theme.utils.AndroidXViewThemeUtils
import com.nextcloud.android.common.ui.theme.utils.DialogViewThemeUtils
import com.nextcloud.android.common.ui.theme.utils.MaterialViewThemeUtils
import javax.inject.Inject

/**
 * Child fields intentionally constructed instead of injected in order to reuse schemes for performance
 */
@IonosCustomization
class ViewThemeUtils @Inject constructor(
    schemes: MaterialSchemes,
    colorUtil: ColorUtil
) : ViewThemeUtilsBase(schemes) {
    private val platformDelegate = AndroidViewThemeUtils(schemes, colorUtil)
    private val androidxDelegate = AndroidXViewThemeUtils(schemes, platformDelegate)

    @JvmField
    val platform = IonosAndroidViewThemeUtils(platformDelegate)

    @JvmField
    val material = IonosMaterialViewThemeUtils(MaterialViewThemeUtils(schemes, colorUtil))

    @JvmField
    val androidx = IonosAndroidXViewThemeUtils(androidxDelegate)

    @JvmField
    val dialog = IonosDialogViewThemeUtils(DialogViewThemeUtils(schemes))

    @JvmField
    val files = IonosFilesSpecificViewThemeUtils(
        schemes,
        FilesSpecificViewThemeUtils(schemes, colorUtil, platformDelegate, androidxDelegate),
    )

    class Factory @Inject constructor(
        private val schemesProvider: MaterialSchemesProvider,
        private val colorUtil: ColorUtil
    ) {
        fun withSchemes(schemes: MaterialSchemes): ViewThemeUtils {
            return ViewThemeUtils(schemes, colorUtil)
        }

        fun withDefaultSchemes(): ViewThemeUtils {
            return withSchemes(schemesProvider.getDefaultMaterialSchemes())
        }

        fun withPrimaryAsBackground(): ViewThemeUtils {
            return withSchemes(schemesProvider.getMaterialSchemesForPrimaryBackground())
        }
    }
}
