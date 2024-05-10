package com.strato.hidrive.util

import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.exceptions.ApiException
import com.strato.hidrive.api.connection.gateway.exceptions.ErrorCode
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable
import retrofit2.Response

fun <T : Any> Observable<Response<T>>.toBodyObservable(): Observable<T> {
	return flatMap { response ->
		val body = response.body()
		if (body != null) {
			Observable.just(body)
		} else {
			Observable.error(response.toApiException())
		}
	}
}

private fun <T : Any> Response<T>.toApiException(): ApiException {
	return ApiException(ErrorCode.byCode(code()), message())
}

fun <RemoteEntity, DomainEntity> Observable<RemoteEntity>.toDomainGatewayResult(
		responseTransformer: ResponseTransformer<RemoteEntity, DomainEntity>,
): Observable<DomainGatewayResult<DomainEntity>> {
	return map(responseTransformer::transform)
			.map(::DomainGatewayResult)
			.onErrorReturn(::DomainGatewayResult)
}
