package com.strato.hidrive.common_ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

public interface ActivityLifecycleListener {
	void onCreate(Bundle savedInstanceState);
	void onStart();
	void onPause();
	void onResume();
	void onStop();
	void onDestroy();
	void onSaveInstanceState(Bundle outState);
	void onActivityResult(int requestCode, int resultCode, Intent data);
	void onNewIntent(Intent intent);
	void onConfigurationChanged(Configuration newConfig);
	boolean onPrepareOptionsMenu(Menu menu);
}
