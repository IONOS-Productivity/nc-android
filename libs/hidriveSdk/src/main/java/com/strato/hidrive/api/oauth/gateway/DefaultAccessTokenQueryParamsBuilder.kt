package com.strato.hidrive.api.oauth.gateway

/**
 * Created by: Alex Kucherenko
 * Date: 04.04.2018.
 */
class DefaultAccessTokenQueryParamsBuilder(
		private val clientId: String,
		private val clientSecret: String,
		private val authorizationCodeType: AuthorizationCodeType,
		private val authCode: String,
		private val grantType: GrantType) {

	/**
	 * Constructs query params map
	 */
	fun build(): Map<String, String> =
			mapOf(
					Pair("client_id", clientId),
					Pair("client_secret", clientSecret),
					Pair("grant_type", grantType.value),
					Pair(authorizationCodeType.value, authCode))
}