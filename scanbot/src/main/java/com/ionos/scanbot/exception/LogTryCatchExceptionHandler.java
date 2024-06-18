package com.ionos.scanbot.exception;

import com.ionos.common_ui.exception.TryCatchExceptionHandler;
import com.ionos.scanbot.util.logger.LoggerUtil;

public class LogTryCatchExceptionHandler extends TryCatchExceptionHandler {

	@Override
	protected void handleException(Exception e) {
		LoggerUtil.logE(getClass().getSimpleName(), e);
	}
}
