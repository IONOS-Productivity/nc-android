package com.strato.hidrive.api.oauth.refresh_token;

/**
 * Created by Sergey Shandyuk on 10/14/2016.
 */

public interface Token {
	String getAccessToken();

	String getRefreshToken();

	Token copy();
}
