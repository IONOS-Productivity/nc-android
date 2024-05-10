package com.strato.hidrive.api.oauth.gateway

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable

/**
 * Created by: Alex Kucherenko
 * Date: 02.03.2018.
 */

class GetAccessTokenGateway<Entity>(
		private val queryParams: Map<String, String>,
		private val apiClientWrapper: ApiClientWrapper,
		private val responseTransformer: ResponseTransformer<GetAccessTokenResponse, Entity>)
	: Gateway<Entity> {

	override fun execute(): Observable<DomainGatewayResult<Entity>> {
		return createRequest()
				.map { DomainGatewayResult(responseTransformer.transform(it)) }
				.onErrorReturn { error -> DomainGatewayResult(error) }
	}

	private fun createRequest(): Observable<GetAccessTokenResponse> =
			apiClientWrapper
					.client
					.create(OauthService::class.java)
					.oauthToken(queryParams)
}