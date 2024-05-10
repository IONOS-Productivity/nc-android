package com.strato.hidrive.api.bll.oauth

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.exceptions.ApiException
import com.strato.hidrive.api.connection.gateway.exceptions.ErrorCode
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by: Alex Kucherenko
 * Date: 26.03.2018.
 *
 * Revoke an active access_token or refresh_token.
 * If you revoke a refresh_token, all related access_token will also be revoked.
 */

class RevokeTokenGateway(private val token: String,
                         private val clientId: String,
                         private val clientSecret: String,
                         private val apiClientWrapper: ApiClientWrapper)
	: Gateway<Boolean> {

	override fun execute(): Observable<DomainGatewayResult<Boolean>> {
		return createRequest()
				.map { response ->
					if (response.isSuccessful) {
						DomainGatewayResult(true)
					} else {
						getErrorResult(response.message())
					}
				}
				.onErrorReturn { error -> DomainGatewayResult(error) }
				.toObservable()
	}

	private fun getErrorResult(message: String?) = DomainGatewayResult<Boolean>(ApiException(ErrorCode.UNKNOWN_ERROR, message))

	private fun createRequest(): Single<Response<ResponseBody>> =
			apiClientWrapper
					.client
					.create(RevokeTokenService::class.java)
					.revoke(clientId, clientSecret, token)
}