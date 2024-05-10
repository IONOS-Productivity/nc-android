package com.strato.hidrive.api.connection.retrofit

import com.strato.hidrive.api.oauth.OAuthRefreshTokenManager
import com.strato.hidrive.api.oauth.refresh_token.RefreshTokenException
import com.strato.hidrive.api.oauth.refresh_token.Token
import com.strato.hidrive.util.Base64Util
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


/**
 * Created by: Alex Kucherenko
 * Date: 23.02.2018.
 */
internal class Authenticator(private val tokenManager: OAuthRefreshTokenManager<out Token>) : Authenticator {

	override fun authenticate(route: Route?, response: Response): Request? =
			try {
				val token = tokenManager.refreshToken().blockingFirst()
				val authorizationHeader = "Bearer ${Base64Util.encodeToString(token.accessToken)}"
				response.request.newBuilder().header("Authorization", authorizationHeader).build()
			} catch (exception: RefreshTokenException) {
				tokenManager.onRefreshTokenError(exception.cause ?: exception)
				null
			}
}