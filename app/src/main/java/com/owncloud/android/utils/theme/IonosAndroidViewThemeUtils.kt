package com.owncloud.android.utils.theme

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import com.nextcloud.android.common.ui.theme.utils.AndroidViewThemeUtils
import com.nextcloud.android.common.ui.theme.utils.ColorRole
import com.owncloud.android.R

class IonosAndroidViewThemeUtils(
    private val platformUtil: AndroidViewThemeUtils,
) {
    fun themeSystemBars(activity: Activity) {
        platformUtil.colorStatusBar(activity, activity.getSystemBarsColor())
    }

    fun themeSystemBars(activity: Activity, @ColorInt color: Int) {
        platformUtil.colorStatusBar(activity, color)
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
