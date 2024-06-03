package com.ionos.domain.exception;

import com.ionos.logger.LoggerUtil;

public class LogTryCatchExceptionHandler extends TryCatchExceptionHandler {

	@Override
	protected void handleException(Exception e) {
		LoggerUtil.logE(e.getMessage(), e);
	}
}
