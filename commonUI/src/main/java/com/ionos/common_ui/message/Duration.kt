package com.ionos.common_ui.message

/**
 * Created by: Alex Kucherenko
 * Date: 25.11.2019.
 */
sealed class Duration {
	object Short : Duration()
	object Long : Duration()
	object Indefinite : Duration()
	class Seconds(val seconds: Int) : Duration()

	companion object {
		@JvmField
		val SHORT = Short
		@JvmField
		val LONG = Long
		@JvmField
		val INDEFINITE = Indefinite
	}
}