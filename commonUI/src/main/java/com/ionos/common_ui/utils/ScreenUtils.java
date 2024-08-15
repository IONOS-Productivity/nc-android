package com.ionos.common_ui.utils;

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

	public static void applyDefaultFontScale(Context context) {
		Configuration configuration = context.getResources().getConfiguration();
		applyDefaultFontScale(configuration);
	}

	public static void applyDefaultFontScale(Configuration configuration) {
		configuration.fontScale = DEFAULT_FONT_SCALE;
	}

}
