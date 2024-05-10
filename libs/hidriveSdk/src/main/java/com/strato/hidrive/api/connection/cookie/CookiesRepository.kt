package com.strato.hidrive.api.connection.cookie

import okhttp3.Cookie

/**
 * User: Dima Muravyov
 * Date: 13.04.2018
 */
interface CookiesRepository {

	fun saveCookies(cookies: List<Cookie>)

	fun getCookies(): List<Cookie>

	fun clearCookies()
}