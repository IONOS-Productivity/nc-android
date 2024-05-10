package com.viseven.develop.passwordprotectionsdk.activity.callback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.List;

public class ExcludeActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
	private final Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
	private final List<Class<? extends Activity>> activitiesToExclude;

	public ExcludeActivityLifecycleCallbacks(List<Class<? extends Activity>> activitiesToExclude,
											 Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
		this.activityLifecycleCallbacks = activityLifecycleCallbacks;
		this.activitiesToExclude = activitiesToExclude;
	}

	@Override
	public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
		}
	}

	@Override
	public void onActivityStarted(@NonNull Activity activity) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivityStarted(activity);
		}
	}

	@Override
	public void onActivityResumed(@NonNull Activity activity) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivityResumed(activity);
		}
	}

	@Override
	public void onActivityPaused(@NonNull Activity activity) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivityPaused(activity);
		}
	}

	@Override
	public void onActivityStopped(@NonNull Activity activity) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivityStopped(activity);
		}
	}

	@Override
	public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivitySaveInstanceState(activity, outState);
		}
	}

	@Override
	public void onActivityDestroyed(@NonNull Activity activity) {
		if (!isActivityExclude(activity.getClass())) {
			this.activityLifecycleCallbacks.onActivityDestroyed(activity);
		}
	}

	private boolean isActivityExclude(Class<? extends Activity> activityClass) {
		for (Class<? extends Activity> activityToExclude : activitiesToExclude) {
			if (activityClass.equals(activityToExclude)) {
				return true;
			}
		}
		return false;
	}
}