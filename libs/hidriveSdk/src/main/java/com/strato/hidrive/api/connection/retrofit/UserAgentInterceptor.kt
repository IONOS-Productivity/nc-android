package com.strato.hidrive.api.connection.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * User: Dima Muravyov
 * Date: 07.09.2018
 */
internal class UserAgentInterceptor(private val userAgent: String) : Interceptor {

	companion object {
		private const val header: String = "User-Agent"
	}

	override fun intercept(chain: Interceptor.Chain): Response =
			chain.proceed(getModifiedRequest(chain))

	private fun getModifiedRequest(chain: Interceptor.Chain): Request =
			chain
					.request()
					.newBuilder()
					.addHeader(header, userAgent)
					.build()
}