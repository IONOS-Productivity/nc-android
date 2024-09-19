package com.strato.hidrive.views.exif_info.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;

/**
 * Created by Anton Shevchuk on 24.06.2016.
 */
public class ScreenConfiguration{

	public boolean large(Context context) {
		Configuration configuration = context.getResources().getConfiguration();
		return (configuration.screenLayout & SCREENLAYOUT_SIZE_MASK) >= SCREENLAYOUT_SIZE_LARGE;
	}

	public boolean landscape(Context context) {
		Point screeSize = realSize(context);
		return screeSize.x > screeSize.y;
	}

	private Point realSize(Context context) {
		Point result = new Point();
		getWindowManager(context).getDefaultDisplay().getRealSize(result);
		return result;
	}

	private WindowManager getWindowManager(Context context) {
		return (WindowManager) context.getSystemService(WINDOW_SERVICE);
	}

}
