package com.strato.hidrive.api.bll.app

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * User: Dima Muravyov
 * Date: 23.02.2018
 */
internal interface AppMeService {

	@GET("app/me?fields=backup,created,developer.email,developer.name,homepage,id,may_publish,name,private_folder,private_folder_id,publication_url,refresh_token.expires,refresh_token.expires_in,refresh_token.instance_id,refresh_token.scope,status")
	fun appMe(): Observable<AppMeResponse>
}