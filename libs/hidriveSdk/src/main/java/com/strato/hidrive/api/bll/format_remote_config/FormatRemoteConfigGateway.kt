package com.strato.hidrive.api.bll.format_remote_config

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import com.strato.hidrive.api.response.entity.FormatRemoteConfigResponse
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable

/**
 * User: Vadym Fedchuk
 * Date: 21.11.2023
 */

class FormatRemoteConfigGateway<Entity> constructor(
    private val apiClientWrapper: ApiClientWrapper,
    private val responseTransformer: ResponseTransformer<FormatRemoteConfigResponse, Entity>
) : Gateway<Entity> {

    override fun execute(): Observable<DomainGatewayResult<Entity>> {
        return createRequest()
            .map { response -> DomainGatewayResult(responseTransformer.transform(response)) }
            .onErrorReturn { error -> DomainGatewayResult(error) }
    }

    private fun createRequest(): Observable<FormatRemoteConfigResponse> =
        apiClientWrapper
            .client
            .create(FormatRemoteConfigService::class.java)
            .getFormatRemoteConfig()
}