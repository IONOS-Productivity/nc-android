package com.strato.hidrive.api.oauth.refresh_token;

import io.reactivex.Observable;

/**
 * Created by Sergey Shandyuk on 10/14/2016.
 */

public interface RefreshTokenGateway<T extends Token> {

	Observable<T> execute() throws RefreshTokenException;

}
