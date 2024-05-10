package com.viseven.develop.passwordprotectionsdk.result_handler;

public interface PasswordProtectionResultHandler {
	void onAccessGranted();

	void onAccessDenied();
}
