package com.ionos.common_ui.exception;

import com.ionos.common_ui.action.Action;
import com.ionos.common_ui.action.ThrowableAction;
import com.ionos.common_ui.action.NullAction;

public abstract class TryCatchExceptionHandler {

	public final void handle(ThrowableAction action) {
		handle(action, NullAction.INSTANCE);
	}

	public final void handle(ThrowableAction action, Action onException) {
		try {
			action.execute();
		} catch (Exception e) {
			handleException(e);
			onException.execute();
		}
	}

	protected abstract void handleException(Exception e);
}
