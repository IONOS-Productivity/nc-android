package com.viseven.develop.passwordprotectionsdk.activity;

public interface PasswordProtectionOwner {
	void registerActivityObserver(PasswordProtectionActivityObserver activityObserver);

	void unregisterActivityObserver(PasswordProtectionActivityObserver activityObserver);
}
