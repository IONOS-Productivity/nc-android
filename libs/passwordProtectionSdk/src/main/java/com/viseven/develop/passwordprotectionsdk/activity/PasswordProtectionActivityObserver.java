package com.viseven.develop.passwordprotectionsdk.activity;

import android.content.Intent;

public interface PasswordProtectionActivityObserver {
	void startActivityForResult(Intent intent, int requestCode, boolean forceSecurityModeAfterFinish);

	void onActivityResult(int requestCode, int resultCode, Intent data);
}
