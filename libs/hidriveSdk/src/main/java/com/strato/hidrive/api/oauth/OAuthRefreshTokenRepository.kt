package com.strato.hidrive.api.oauth

import com.strato.hidrive.api.oauth.refresh_token.Token

/**
 * Created by Shevchuk Anton on 13/08/18.
 */
interface OAuthRefreshTokenRepository<T : Token> {

	val token: T

	fun saveToken(t: T)

	fun hasToken(): Boolean

	fun clearToken()
}
