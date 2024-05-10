/**
 * Copyright 2014 STRATO AG
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strato.hidrive.api.connection.gateway;

import com.strato.hidrive.api.connection.gateway.interfaces.RefreshTokenErrorGlobalHandler;
import com.strato.hidrive.api.oauth.OAuthRefreshTokenManager;
import com.strato.hidrive.api.oauth.OAuthRefreshTokenRepository;
import com.strato.hidrive.api.oauth.refresh_token.Token;

public class HiDriveGatewaySettings {
	private static HiDriveGatewaySettings instance = null;

	private String hiDriveApiUrl;

	private OAuthRefreshTokenManager tokenManager;
	private RefreshTokenErrorGlobalHandler globalRefreshTokenErrorHandler = null;
	private OAuthRefreshTokenRepository<? extends Token> tokenRepository;

	public static HiDriveGatewaySettings getInstance() {
		if (instance == null) {
			instance = new HiDriveGatewaySettings();
		}
		return instance;
	}

	private HiDriveGatewaySettings() {
	}

	public void initialize(String hiDriveApiUrl,
						   OAuthRefreshTokenManager tokenManager,
						   OAuthRefreshTokenRepository<? extends Token> tokenRepository) {
		this.hiDriveApiUrl = hiDriveApiUrl;
		this.tokenManager = tokenManager;
		this.tokenRepository = tokenRepository;
	}

	public RefreshTokenErrorGlobalHandler getGlobalRefreshTokenErrorHandler() {
		return globalRefreshTokenErrorHandler;
	}

	public void setGlobalRefreshTokenErrorHandler(RefreshTokenErrorGlobalHandler globalRefreshTokenErrorHandler) {
		this.globalRefreshTokenErrorHandler = globalRefreshTokenErrorHandler;
	}

	public String getHiDriveApiUrl() {
		return hiDriveApiUrl;
	}

	public OAuthRefreshTokenManager getTokenManager() {
		return tokenManager;
	}

	public OAuthRefreshTokenRepository getTokenRepository() {
		return tokenRepository;
	}

	public boolean isAuthorized() {
		return tokenManager != null && tokenRepository.hasToken();
	}
}
