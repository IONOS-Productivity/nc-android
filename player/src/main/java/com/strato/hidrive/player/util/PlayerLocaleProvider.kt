package com.strato.hidrive.player.util

import java.util.Locale

fun interface PlayerLocaleProvider {

	fun getDefault(): Locale
}