package com.strato.hidrive.api.bll.format_remote_config

import com.strato.hidrive.api.response.entity.FormatRemoteConfigResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * User: Vadym Fedchuk
 * Date: 21.11.2023
 */

interface FormatRemoteConfigService {

    @GET("config/fileMapping.json")
    fun getFormatRemoteConfig(): Observable<FormatRemoteConfigResponse>
}