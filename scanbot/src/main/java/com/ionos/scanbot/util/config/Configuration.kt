package com.ionos.scanbot.util.config

import android.content.res.Configuration

private const val DEFAULT_FONT_SCALE = 1.0f

internal fun Configuration.applyDefaultFontScale() {
    fontScale = DEFAULT_FONT_SCALE
}
