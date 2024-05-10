package com.strato.hidrive.common_ui.window;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

//import com.strato.hidrive.common_ui.BuildConfig;

/**
 * User: Dima Muravyov
 * Date: 06.09.2016
 */
public class WindowWrapper {
	private final Window window;

	public WindowWrapper(Window window) {
		this.window = window;
	}

	public void setStatusBarColor(@ColorInt int colorInt) {
		this.window.setStatusBarColor(colorInt);
	}
}
