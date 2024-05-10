package com.strato.hidrive.common_ui.activity;

/**
 * Created by Anton Shevchuk on 11.07.2016.
 */
public interface RegisterableActivity {
	void registerActivityLifecycleListener(ActivityLifecycleListener listener);

	void unregisterActivityLifecycleListener(ActivityLifecycleListener listener);
}
