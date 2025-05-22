package com.ionos.player.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;


public class ScreenUtils {

	private ScreenUtils() {
	}


	@SuppressWarnings("deprecation")
	public static int getDisplayWidth(Context context) {
		return getDisplay(context).getWidth();
	}

	@SuppressWarnings("deprecation")
	public static int getDisplayHeight(Context context) {
		return getDisplay(context).getHeight();
	}

	protected static Display getDisplay(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay();
	}

}
