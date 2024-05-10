package com.strato.hidrive.util

/**
 * Created by: Alex Kucherenko
 * Date: 30.04.2018.
 */
internal class Base64Util {

	companion object {

		fun encodeToString(input: String): String = Base64.encodeToString(input.toByteArray(), Base64.NO_WRAP)
	}
}