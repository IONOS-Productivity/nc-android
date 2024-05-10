package com.strato.hidrive.common_ui.utils;

import static android.content.Context.WINDOW_SERVICE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by Anton Shevchuk on 24.06.2016.
 */
public class ScreenConfiguration implements IScreenConfiguration {

	@Override
	public boolean large(Context context) {
		Configuration configuration = context.getResources().getConfiguration();
		return (configuration.screenLayout & SCREENLAYOUT_SIZE_MASK) >= SCREENLAYOUT_SIZE_LARGE;
	}

	@Override
	public boolean landscape(Context context) {
		Point screeSize = realSize(context);
		return screeSize.x > screeSize.y;
	}

	@Override
	public Point realSize(Context context) {
		Point result = new Point();
		getWindowManager(context).getDefaultDisplay().getRealSize(result);
		return result;
	}

	private WindowManager getWindowManager(Context context) {
		return (WindowManager) context.getSystemService(WINDOW_SERVICE);
	}
}
