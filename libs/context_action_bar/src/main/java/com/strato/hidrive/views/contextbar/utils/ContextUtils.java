package com.strato.hidrive.views.contextbar.utils;

import android.app.Activity;
import android.content.Context;

public class ContextUtils {

	public static boolean isActivityFinishing(Context context) {
		return context instanceof Activity && ((Activity) context).isFinishing();
	}
}
