package com.strato.hidrive.domain.exception;

import com.strato.hidrive.domain.logger.LoggerUtil;

public class LogTryCatchExceptionHandler extends TryCatchExceptionHandler {

	@Override
	protected void handleException(Exception e) {
		LoggerUtil.logE(e.getMessage(), e);
	}
}
