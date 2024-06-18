package com.ionos.common_ui.dialog.actions.null_objects;

import com.ionos.common_ui.dialog.actions.Action;

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
