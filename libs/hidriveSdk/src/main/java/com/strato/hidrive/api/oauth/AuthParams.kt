package com.strato.hidrive.api.oauth

/**
 * Created by: Alex Kucherenko
 * Date: 06.10.2017.
 */
data class AuthParams(val oauthCallbackHostUrl: String,
                      val oauthCodeQueryKey: String,
                      val oauthScopeQueryKey: String,
                      val minimalScope: String,
                      val loginUrl: String)