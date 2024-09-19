package com.strato.hidrive.views.contextbar.utils;

import android.content.Context;
import android.content.res.Configuration;

import static android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
import static android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK;

/**
 * Created by Anton Shevchuk on 24.06.2016.
 */
public class ScreenConfiguration {

	public boolean large(Context context) {
		Configuration configuration = context.getResources().getConfiguration();
		return (configuration.screenLayout & SCREENLAYOUT_SIZE_MASK) >= SCREENLAYOUT_SIZE_LARGE;
	}

}
