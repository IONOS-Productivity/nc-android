package com.strato.hidrive.common_ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import com.strato.hidrive.common_ui.utils.ScreenUtils;
//import com.viseven.develop.passwordprotectionsdk.activity.PasswordProtectionActivityObserver;
//import com.viseven.develop.passwordprotectionsdk.activity.PasswordProtectionOwner;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpyableActivity
		extends AppCompatActivity
		implements RegisterableActivity/*, PasswordProtectionOwner*/ {

	private final List<ActivityLifecycleListener> lifecycleListeners = new ArrayList<>();
//	private final List<PasswordProtectionActivityObserver> activityObservers = new ArrayList<>();

	@Override
	public void registerActivityLifecycleListener(ActivityLifecycleListener listener) {
		if (!lifecycleListeners.contains(listener)) {
			this.lifecycleListeners.add(listener);
		}
	}

	@Override
	public void unregisterActivityLifecycleListener(ActivityLifecycleListener listener) {
		this.lifecycleListeners.remove(listener);
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		ScreenUtils.applyDefaultFontScale(newBase);
		super.attachBaseContext(newBase);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onCreate(savedInstanceState);
		}
	}

	@Override
	@Deprecated(forRemoval = true)
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onPause();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		ScreenUtils.applyDefaultFontScale(newConfig);
		super.onConfigurationChanged(newConfig);

		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onConfigurationChanged(newConfig);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onResume();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onStart();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onStop();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onActivityResult(requestCode, resultCode, data);
		}
//		for (PasswordProtectionActivityObserver activityObserver : activityObservers) {
//			activityObserver.onActivityResult(requestCode, resultCode, data);
//		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onDestroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onSaveInstanceState(outState);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		for (int i = 0; i < this.lifecycleListeners.size(); i++) {
			this.lifecycleListeners.get(i).onPrepareOptionsMenu(menu);
		}
		return false;
	}

//	@Override
//	public void startActivityForResult(Intent intent, int requestCode) {
//		startActivityForResult(intent, requestCode, false);
//	}

//	public final void startActivity(Intent intent, boolean forceSecurityModeAfterFinish) {
//		startActivityForResult(intent, -1, forceSecurityModeAfterFinish);
//	}

//	public final void startActivityForResult(Intent intent, int requestCode, boolean forceSecurityModeAfterFinish) {
//		super.startActivityForResult(intent, requestCode);
//		for (PasswordProtectionActivityObserver activityObserver : activityObservers) {
//			activityObserver.startActivityForResult(intent, requestCode, forceSecurityModeAfterFinish);
//		}
//	}
//
//	@Override
//	public void registerActivityObserver(PasswordProtectionActivityObserver activityObserver) {
//		this.activityObservers.add(activityObserver);
//	}
//
//	@Override
//	public void unregisterActivityObserver(PasswordProtectionActivityObserver activityObserver) {
//		this.activityObservers.remove(activityObserver);
//	}
}
