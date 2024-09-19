/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Your Name <your@email.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.owncloud.android.utils.theme

import android.app.Activity
import android.content.Context
import androidx.annotation.ColorInt
import com.ionos.annotation.IonosCustomization
import com.nextcloud.android.common.ui.theme.utils.AndroidViewThemeUtils
import com.owncloud.android.R

@IonosCustomization
class IonosViewThemeUtils(private val platform: AndroidViewThemeUtils) {

    fun themeSystemBars(activity: Activity) {
        platform.colorStatusBar(activity, activity.getSystemBarsColor())
    }

    fun resetSystemBars(activity: Activity) {
        platform.colorStatusBar(activity, activity.getSystemBarsColor())
    }

    @ColorInt
    private fun Context.getSystemBarsColor(): Int = getColor(R.color.system_bars_color)
}