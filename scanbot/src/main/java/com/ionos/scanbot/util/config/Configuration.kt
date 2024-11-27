/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.util.config

import android.content.res.Configuration

private const val DEFAULT_FONT_SCALE = 1.0f

internal fun Configuration.applyDefaultFontScale() {
    fontScale = DEFAULT_FONT_SCALE
}
