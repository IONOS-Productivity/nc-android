package com.strato.hidrive.api.connection.retrofit

import okhttp3.Interceptor

/**
 * Handles errors returned by server.
 */
class ErrorInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain) =
			chain.proceed(chain.request())
					.apply {
						if (!this.isSuccessful) {
							throw ErrorResponseParser().parse(this)
						}
					}
}
