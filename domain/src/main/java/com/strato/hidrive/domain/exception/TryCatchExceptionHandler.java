package com.strato.hidrive.domain.exception;

import com.strato.hidrive.domain.interfaces.actions.Action;
import com.strato.hidrive.domain.interfaces.actions.ThrowableAction;
import com.strato.hidrive.domain.interfaces.actions.null_objects.NullAction;

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
