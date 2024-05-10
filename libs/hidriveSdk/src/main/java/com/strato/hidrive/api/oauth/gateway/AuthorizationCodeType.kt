package com.strato.hidrive.api.oauth.gateway

/**
 * User: Dima Muravyov
 * Date: 11.04.2018
 */
enum class AuthorizationCodeType(val value: String) {
	ID_TOKEN("id_token"),
	CODE("code"),
	REFRESH_TOKEN("refresh_token")
}