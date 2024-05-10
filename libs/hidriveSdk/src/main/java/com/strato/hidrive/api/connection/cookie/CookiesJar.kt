package com.strato.hidrive.api.connection.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * User: Dima Muravyov
 * Date: 03.05.2018
 */
class CookiesJar(private val cookiesRepository: CookiesRepository) : CookieJar {

	override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
		cookies.let {
			cookiesRepository.clearCookies()
			cookiesRepository.saveCookies(cookies)
		}
	}

	override fun loadForRequest(url: HttpUrl): List<Cookie> {
		return cookiesRepository.getCookies().toMutableList()
	}
}