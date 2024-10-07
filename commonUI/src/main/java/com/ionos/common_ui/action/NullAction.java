package com.ionos.common_ui.action;

/**
 * User: zuzik
 * Date: 6/16/16
 */
public class NullAction implements Action {

	public static final NullAction INSTANCE = new NullAction();

	private NullAction() {
	}

	@Override
	public void execute() {
	}
}
