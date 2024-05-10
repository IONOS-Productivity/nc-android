package com.strato.hidrive.api.connection.retrofit

import com.strato.hidrive.api.connection.gateway.interfaces.AccessTokenProvider
import com.strato.hidrive.util.Base64Util
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by: Alex Kucherenko
 * Date: 21.02.2018.
 */
internal class AuthInterceptor(private val tokenProvider: AccessTokenProvider) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val accessToken = "Bearer ${Base64Util.encodeToString(tokenProvider.provide())}"
		val requestBuilder = chain
				.request()
				.newBuilder()
				.header("Authorization", accessToken)

		val request = requestBuilder.build()
		return chain.proceed(request)
	}
}