package com.strato.hidrive.common_ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

import com.strato.hidrive.common_ui.activity.ActivityLifecycleListener;

public abstract class BasicActivityLifecycleListener implements ActivityLifecycleListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {}
	@Override
	public void onStart() {}
	@Override
	public void onPause() {}
	@Override
	public void onResume() {}
	@Override
	public void onStop() {}
	@Override
	public void onDestroy() {}
	@Override
	public void onSaveInstanceState(Bundle outState) {}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {}
	@Override
	public void onNewIntent(Intent intent) {}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) { return false; }
}
