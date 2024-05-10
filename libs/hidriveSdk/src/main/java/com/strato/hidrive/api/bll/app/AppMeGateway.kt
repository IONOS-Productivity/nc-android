package com.strato.hidrive.api.bll.app

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable

class AppMeGateway<Entity>(private val apiClientWrapper: ApiClientWrapper,
						   private val responseTransformer: ResponseTransformer<AppMeResponse, Entity>)
	: Gateway<Entity> {

	override fun execute(): Observable<DomainGatewayResult<Entity>> =
			createRequest()
					.map { response -> DomainGatewayResult(responseTransformer.transform(response)) }
					.onErrorReturn { error -> DomainGatewayResult(error) }

	private fun createRequest(): Observable<AppMeResponse> =
			apiClientWrapper
					.client
					.create(AppMeService::class.java)
					.appMe()
}