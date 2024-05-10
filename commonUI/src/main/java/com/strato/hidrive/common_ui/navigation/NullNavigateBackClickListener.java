package com.strato.hidrive.common_ui.navigation;

/**
 * User: zuzik
 * Date: 7/28/16
 */
public class NullNavigateBackClickListener implements NavigateBackClickListener {

	public static final NullNavigateBackClickListener INSTACNE = new NullNavigateBackClickListener();

	private NullNavigateBackClickListener() {
	}

	@Override
	public boolean onNavigateBackClicked() {
		return false;
	}
}
