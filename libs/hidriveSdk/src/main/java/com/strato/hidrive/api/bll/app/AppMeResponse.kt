package com.strato.hidrive.api.bll.app

/**
 * User: Dima Muravyov
 * Date: 23.02.2018
 */
data class AppMeResponse(val created: Long?,
						 val developer: DeveloperResponse?,
						 val homepage: String?,
						 val id: Int?,
						 val may_publish: Boolean?,
						 val name: String?,
						 val private_folder: String?,
						 val private_folder_id: String?,
						 val publication_url: String?,
						 val refresh_token: RefreshTokenResponse?,
						 val status: String?) {

	data class DeveloperResponse(val email: String?,
								 val name: String?)

	data class RefreshTokenResponse(val expires: Long?,
									val expires_in: Int?,
									val instance_id: String?,
									val scope: String?)
}
