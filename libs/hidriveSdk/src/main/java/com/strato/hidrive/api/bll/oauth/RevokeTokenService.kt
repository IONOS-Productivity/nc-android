package com.strato.hidrive.api.bll.oauth

import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by: Alex Kucherenko
 * Date: 26.03.2018.
 */
internal interface RevokeTokenService {

	@FormUrlEncoded
	@POST("oauth2/revoke")
	fun revoke(@Field("client_id") clientId: String,
			   @Field("client_secret") clientSecret: String,
			   @Field("token") grantType: String)
			: Single<Response<ResponseBody>>
}