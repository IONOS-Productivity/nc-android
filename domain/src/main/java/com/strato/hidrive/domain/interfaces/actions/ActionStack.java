package com.strato.hidrive.domain.interfaces.actions;

import java.util.Stack;

/**
 * User: Dima Muravyov
 * Date: 27.01.2017
 */

public class ActionStack {
	private final Stack<Action> actionStack = new Stack<>();

	public void executeStack() {
		while (!this.actionStack.isEmpty()) {
			this.actionStack.pop().execute();
		}
	}

	public void push(Action action) {
		if (action != null) {
			this.actionStack.push(action);
		}
	}
}
