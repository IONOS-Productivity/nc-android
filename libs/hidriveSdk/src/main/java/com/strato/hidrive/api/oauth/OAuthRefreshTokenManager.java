package com.strato.hidrive.api.oauth;

import com.strato.hidrive.api.oauth.refresh_token.Token;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

/**
 * Created by yuriydazhuk on 8/5/15.
 */
public interface OAuthRefreshTokenManager<T extends Token> {

	Observable<T> refreshToken();

	void revoke();

	void onRefreshTokenError(@NotNull Throwable throwable);
}
