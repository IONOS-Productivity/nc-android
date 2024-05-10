package com.strato.hidrive.api.bll.permission

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable

/**
 * User: Dima Muravyov
 * Date: 11.04.2018
 */

class PermissionInfoGateway<Entity>(private val pathToCheck: String,
									private val apiClientWrapper: ApiClientWrapper,
									private val responseTransformer: ResponseTransformer<PermissionInfoResponse, Entity>)
	: Gateway<Entity> {

	override fun execute(): Observable<DomainGatewayResult<Entity>> =
			createRequest()
					.map { response -> DomainGatewayResult(responseTransformer.transform(response)) }
					.onErrorReturn { error -> DomainGatewayResult(error) }

	private fun createRequest(): Observable<PermissionInfoResponse> =
			apiClientWrapper
					.client
					.create(PermissionInfoService::class.java)
					.getPermission(pathToCheck)
}