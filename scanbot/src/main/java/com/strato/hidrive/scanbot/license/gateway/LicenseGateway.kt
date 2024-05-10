package com.strato.hidrive.scanbot.license.gateway

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import com.strato.hidrive.api.connection.gateway.DomainGatewayResult
import com.strato.hidrive.api.connection.gateway.interfaces.Gateway
import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import okhttp3.ResponseBody

/**
 * User: Dima Muravyov
 * Date: 06.02.2020
 */
internal class LicenseGateway(
	private val licenseKeyUrl: String,
	private val apiClientWrapper: ApiClientWrapper,
	private val responseTransformer: ResponseTransformer<ResponseBody, String?>,
) : Gateway<String> {

	override fun execute(): Observable<DomainGatewayResult<String>> {
		return createRequest()
				.compose(getScanbotLicenseKeyResponseTransformer())
				.map { scanbotLicenseKey -> DomainGatewayResult(scanbotLicenseKey) }
				.onErrorReturn { error -> DomainGatewayResult(error) }
				.toObservable()
	}

	private fun createRequest(): Single<ResponseBody> {
		return apiClientWrapper
				.client
				.create(LicenseService::class.java)
				.loadLicense(licenseKeyUrl)
	}

	private fun getScanbotLicenseKeyResponseTransformer(): SingleTransformer<ResponseBody, String> =
			SingleTransformer { upstream ->
				upstream
						.flatMap { response ->
							responseTransformer.transform(response)
									?.let { licenseKey -> Single.just(licenseKey) }
									?: let { Single.error(Throwable("${this::class.java.simpleName} Can't parse response.")) }
						}
			}
}