package com.strato.hidrive.api.oauth.gateway

/**
 * Created by: Alex Kucherenko
 * Date: 27.02.2018.
 */
data class GetAccessTokenResponse(val access_token: String?,
								  val alias: String?,
								  val refresh_token: String?,
								  val expires_in: Int?,
								  val scope: String?,
								  val token_type: String?,
								  val userid: String?)