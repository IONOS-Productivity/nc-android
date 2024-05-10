package com.strato.hidrive.api.connection.gateway;

import com.strato.hidrive.api.connection.gateway.interfaces.AccessTokenProvider;
import com.strato.hidrive.api.oauth.OAuthRefreshTokenRepository;
import com.strato.hidrive.api.oauth.refresh_token.Token;

/**
 * Created by: Alex Kucherenko
 * Date: 21.04.2017.
 */

public class PrivateFolderAccessTokenProvider implements AccessTokenProvider {
	private OAuthRefreshTokenRepository<? extends Token> tokenOAuthRefreshTokenRepository;

	public PrivateFolderAccessTokenProvider(OAuthRefreshTokenRepository<? extends Token> tokenOAuthRefreshTokenRepository) {
		this.tokenOAuthRefreshTokenRepository = tokenOAuthRefreshTokenRepository;
	}

	@Override
	public String provide() {
		return this.tokenOAuthRefreshTokenRepository.getToken().getAccessToken();
	}
}
