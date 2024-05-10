package com.strato.hidrive.common_ui.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.view.Display;
import android.view.WindowManager;


public class ScreenUtils {

	private static final float DEFAULT_FONT_SCALE = 1.0f;

	private ScreenUtils() {
	}

	public static int getOrientation(Context context) {
		Configuration config = context.getApplicationContext().getResources().getConfiguration();
		return config.orientation;
	}

	public static boolean isPortraitOrientation(Context context) {
		return getOrientation(context) == Configuration.ORIENTATION_PORTRAIT;
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

	public static void applyDefaultFontScale(Context context) {
		Configuration configuration = context.getResources().getConfiguration();
		applyDefaultFontScale(configuration);
	}

	public static void applyDefaultFontScale(Configuration configuration) {
		configuration.fontScale = DEFAULT_FONT_SCALE;
	}

	public static boolean isScreenOn(Context context) {
		boolean isLocked;

		KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		boolean inKeyguardRestrictedInputMode = keyguardManager.inKeyguardRestrictedInputMode();

		if (inKeyguardRestrictedInputMode) {
			isLocked = true;

		} else {
			PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			isLocked = !powerManager.isInteractive();
		}
		return !isLocked;
	}
}
