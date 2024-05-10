package com.strato.hidrive.scanbot.license.gateway

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * User: Dima Muravyov
 * Date: 06.02.2020
 */
internal interface LicenseService {
	@GET
	fun loadLicense(@Url url: String?): Single<ResponseBody>
}
