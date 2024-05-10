package com.strato.hidrive.api.connection.gateway;

import com.strato.hidrive.api.connection.gateway.interfaces.AccessTokenProvider;

/**
 * Created by: Alex Kucherenko
 * Date: 21.04.2017.
 */
public class DefaultAccessTokenProvider implements AccessTokenProvider {
	@Override
	public String provide() {
		return HiDriveGatewaySettings.getInstance().getTokenRepository().getToken().getAccessToken();
	}
}
