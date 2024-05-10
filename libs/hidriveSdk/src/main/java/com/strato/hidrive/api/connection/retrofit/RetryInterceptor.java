package com.strato.hidrive.api.connection.retrofit;

import com.strato.hidrive.api.connection.gateway.exceptions.ErrorCode;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
	private int retryCount;
	private ErrorCode errorCode;

	public RetryInterceptor(int retryCount, ErrorCode errorCode) {
		this.retryCount = retryCount;
		this.errorCode = errorCode;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Response response = chain.proceed(request);
		int tryCount = 0;
		while (requestShouldBeRetried(response) && tryCount < retryCount) {
			tryCount++;
			response.close();
			response = chain.proceed(request);
		}
		return response;
	}

	private boolean requestShouldBeRetried(Response response) {
		return this.errorCode.equals(ErrorCode.Companion.byCode(response.code()));
	}
}
