package com.strato.hidrive.domain.manager;

import com.strato.hidrive.domain.interfaces.actions.Action;

/**
 * Created by Anton Shevchuk on 19.08.2016.
 */

public class TimeSkippableActionExecutor {

	private static final int DEFAULT_ALLOWED_REPEAT_DELAY = 1000;
	private double lastExecutionTime;

	public void execute(Action action) {
		execute(action, DEFAULT_ALLOWED_REPEAT_DELAY);
	}

	public void execute(Action action, int allowedRepeatDelay) {
		long currentMillis = System.currentTimeMillis();
		if (currentMillis > this.lastExecutionTime + allowedRepeatDelay) {
			this.lastExecutionTime = currentMillis;
			action.execute();
		}
	}
}
