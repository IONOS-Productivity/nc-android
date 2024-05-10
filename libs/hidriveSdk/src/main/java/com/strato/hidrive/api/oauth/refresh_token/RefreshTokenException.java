package com.strato.hidrive.api.oauth.refresh_token;

/**
 * Created by Sergey Shandyuk on 10/14/2016.
 */

public class RefreshTokenException extends RuntimeException {
	public RefreshTokenException(String detailMessage) {
		super(detailMessage);
	}
}
