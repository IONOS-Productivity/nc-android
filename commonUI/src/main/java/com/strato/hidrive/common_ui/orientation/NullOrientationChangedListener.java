package com.strato.hidrive.common_ui.orientation;

/**
 * User: zuzik
 * Date: 7/25/16
 */
public class NullOrientationChangedListener implements OrientationChangedListener{

	public static final NullOrientationChangedListener INSTANCE = new NullOrientationChangedListener();

	private NullOrientationChangedListener() {
	}

	@Override
	public void onOrientationChanged(int orientation) {

	}
}
