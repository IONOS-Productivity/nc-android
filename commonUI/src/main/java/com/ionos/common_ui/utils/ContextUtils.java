package com.ionos.common_ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContextUtils {

	private ContextUtils() {
		throw new AssertionError();
	}

	@Nullable
	public static Activity getActivity(@NonNull Context context) {
		return getActivity(context, Activity.class);
	}

	@Nullable
	public static <T> T getActivity(@NonNull Context context, @NonNull Class<T> activityType) {
		if (activityType.isInstance(context)) {
			return activityType.cast(context);
		} else if (context instanceof ContextWrapper) {
			return getActivity(((ContextWrapper) context).getBaseContext(), activityType);
		} else {
			return null;
		}
	}

	public static boolean isActivityFinishing(Context context) {
		return context instanceof Activity && ((Activity) context).isFinishing();
	}
}
