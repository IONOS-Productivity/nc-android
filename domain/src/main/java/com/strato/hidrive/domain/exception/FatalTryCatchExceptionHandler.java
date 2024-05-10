package com.strato.hidrive.domain.exception;

import com.strato.hidrive.domain.logger.LoggerUtil;

public class FatalTryCatchExceptionHandler extends TryCatchExceptionHandler {

	@Override
	protected void handleException(Exception e) {
		LoggerUtil.logE(e.getMessage(), e);
		throw new RuntimeException(e.getMessage());
	}
}
