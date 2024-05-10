package com.strato.hidrive.api.connection.gateway.interfaces

/**
 * User: Dima Muravyov
 * Date: 29.03.2018
 */
interface RefreshTokenErrorGlobalHandler {

	fun onError(error: Throwable)
}