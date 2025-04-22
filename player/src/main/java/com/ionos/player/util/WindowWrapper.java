package com.ionos.player.util;

import android.view.Window;
import android.view.WindowManager;

/**
 * User: Dima Muravyov
 * Date: 06.09.2016
 */
public class WindowWrapper {
	private final Window window;

	public WindowWrapper(Window window) {
		this.window = window;
	}

	public void setFullScreenFlags() {
		this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void clearFullScreenFlags() {
		this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

}
