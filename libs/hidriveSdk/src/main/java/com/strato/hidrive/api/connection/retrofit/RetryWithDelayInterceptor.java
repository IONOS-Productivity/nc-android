package com.strato.hidrive.api.connection.retrofit;

import com.strato.hidrive.api.connection.gateway.exceptions.ErrorCode;
import com.strato.hidrive.domain.logger.LoggerUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryWithDelayInterceptor implements Interceptor {
	private int retryCount;
	private long delay;
	private ErrorCode errorCode;

	public RetryWithDelayInterceptor(int retryCount, int delayInSeconds, ErrorCode errorCode) {
		this.retryCount = retryCount;
		this.delay = delayInSeconds;
		this.errorCode = errorCode;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Response response = chain.proceed(request);
		int tryCount = 0;
		while (requestShouldBeRetried(response) && tryCount < retryCount) {
			try {
				if (tryCount > 0) {
					Thread.sleep(delay * 1000);
				}
				tryCount++;
				response.close();
				response = chain.proceed(request);
			} catch (InterruptedException e) {
				LoggerUtil.logE(getClass().getSimpleName(), e);
			}
		}
		return response;
	}

	private boolean requestShouldBeRetried(Response response) {
		return this.errorCode.equals(ErrorCode.Companion.byCode(response.code()));
	}
}
