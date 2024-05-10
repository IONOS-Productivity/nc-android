package com.viseven.develop.passwordprotectionsdk.activity;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.viseven.develop.passwordprotectionsdk.activity.callback.ActivityLifecycleCallbacksAdapter;

import java.util.ArrayList;
import java.util.List;

public final class PasswordProtectionActivityTracker {
	private static final PasswordProtectionActivityTracker INSTANCE = new PasswordProtectionActivityTracker();

	@Nullable
	private Activity topActivity = null;
	private List<Class<? extends Activity>> activitiesToExclude = new ArrayList<>();

	private PasswordProtectionActivityTracker() {
	}

	public static PasswordProtectionActivityTracker get() {
		return INSTANCE;
	}

	public void init(Application application, List<Class<? extends Activity>> activitiesToExclude) {
		this.activitiesToExclude = new ArrayList<>(activitiesToExclude);
		application.unregisterActivityLifecycleCallbacks(this.activityLifecycleCallback);
		application.registerActivityLifecycleCallbacks(this.activityLifecycleCallback);
	}

	/*Returns top visible activity*/
	@Nullable
	public final Activity getTopActivity() {
		return this.topActivity;
	}

	private ActivityLifecycleCallbacksAdapter activityLifecycleCallback = new ActivityLifecycleCallbacksAdapter() {
		@Override
		public void onActivityStarted(@NonNull Activity activity) {
			super.onActivityStarted(activity);
			if (!activitiesToExclude.contains(activity.getClass())) {
				PasswordProtectionActivityTracker.this.topActivity = activity;
			}
		}

		@Override
		public void onActivityDestroyed(@NonNull Activity activity) {
			super.onActivityDestroyed(activity);
			if (activity.equals(PasswordProtectionActivityTracker.this.topActivity)) {
				PasswordProtectionActivityTracker.this.topActivity = null;
			}
		}
	};
}
