/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Your Name <your@email.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.owncloud.android.utils.theme

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.ionos.annotation.IonosCustomization
import com.nextcloud.android.common.ui.theme.utils.AndroidViewThemeUtils
import com.nextcloud.android.common.ui.theme.utils.ColorRole
import com.nextcloud.android.common.ui.theme.utils.MaterialViewThemeUtils
import com.owncloud.android.R

@IonosCustomization
class IonosViewThemeUtils(
    private val platformUtil: AndroidViewThemeUtils,
    private val materialUtil: MaterialViewThemeUtils,
) {

    @JvmField
    val platform = Platform()

    @JvmField
    val material = Material()

    inner class Platform {
        fun themeSystemBars(activity: Activity) {
            platformUtil.colorStatusBar(activity, activity.getSystemBarsColor())
        }

        fun resetSystemBars(activity: Activity) {
            platformUtil.colorStatusBar(activity, activity.getSystemBarsColor())
        }

        @JvmOverloads
        fun colorViewBackground(view: View, colorRole: ColorRole = ColorRole.SURFACE) {
            view.setBackgroundColor(colorRole.getColor(view.context))
        }

        fun themeDialog(view: View) {
            colorViewBackground(view)
        }

        @ColorInt
        private fun ColorRole.getColor(context: Context): Int = when (this) {
            /* ColorRole.SURFACE used for dialog & bottomSheet background, navigation & system bar color */
            ColorRole.SURFACE -> context.getColor(R.color.ionos_bottom_sheet_background_color)
            else -> throw IllegalStateException("Not implemented")
        }

        @ColorInt
        private fun Context.getSystemBarsColor(): Int = getColor(R.color.system_bars_color)
    }

    inner class Material {
        fun colorMaterialButtonPrimaryTonal(button: MaterialButton) {}
        fun colorMaterialButtonPrimaryBorderless(button: MaterialButton) {}
        fun colorMaterialButtonPrimaryFilled(folderPickerBtnCancel: MaterialButton) {}
        fun colorMaterialButtonText(folderPickerBtnCancel: MaterialButton) {}
        fun themeSnackbar(snackbar: Snackbar) {}
    }
}