package com.strato.hidrive.api.oauth.gateway

import com.strato.hidrive.api.oauth.gateway.token_info.TokenInfoResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by: Alex Kucherenko
 * Date: 02.03.2018.
 */
internal interface OauthService {

	@FormUrlEncoded
	@POST("oauth2/token")
	fun oauthToken(@FieldMap params: Map<String, String>): Observable<GetAccessTokenResponse>

	@FormUrlEncoded
	@POST("oauth2/tokeninfo")
	fun getTokenInfo(@Field("access_token") accessToken: String): Observable<TokenInfoResponse>
}