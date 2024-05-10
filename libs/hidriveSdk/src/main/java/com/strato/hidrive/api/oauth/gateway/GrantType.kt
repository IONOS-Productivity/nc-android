package com.strato.hidrive.api.oauth.gateway

/**
 * User: Dima Muravyov
 * Date: 11.04.2018
 */
enum class GrantType(val value: String) {
	ID_TOKEN("id_token"),
	AUTHORIZATION_CODE("authorization_code"),
	REFRESH_TOKEN("refresh_token")
}