@file:JvmName("ContextUtils")

package com.ionos.utils.context

import android.content.Context
import android.content.res.Configuration

fun Context.isDarkMode(): Boolean {
    val uiMode = resources.configuration.uiMode
    return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}
