package com.strato.hidrive.api.bll.permission

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * User: Dima Muravyov
 * Date: 11.04.2018
 */
internal interface PermissionInfoService {

	@GET("permission")
	fun getPermission(@Query("path") pathToCheck: String): Observable<PermissionInfoResponse>
}