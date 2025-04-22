package com.ionos.player.util

import java.util.Locale

fun interface PlayerLocaleProvider {

	fun getDefault(): Locale
}