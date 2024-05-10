package com.strato.hidrive.api.oauth.gateway.token_info

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import com.strato.hidrive.api.oauth.gateway.OauthService
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable

/**
 * Created by: Alex Kucherenko
 * Date: 02.03.2018.
 */

class GetTokenInfoGateway<Entity>(
		private val accessToken: String,
		private val apiClientWrapper: ApiClientWrapper,
		private val responseTransformer: ResponseTransformer<TokenInfoResponse, Entity>)
	: Gateway<Entity> {

	override fun execute(): Observable<DomainGatewayResult<Entity>> {
		return createRequest()
				.map { DomainGatewayResult(responseTransformer.transform(it)) }
				.onErrorReturn { error -> DomainGatewayResult(error) }
	}

	private fun createRequest(): Observable<TokenInfoResponse> =
			apiClientWrapper
					.client
					.create(OauthService::class.java)
					.getTokenInfo(accessToken)
}