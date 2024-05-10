package com.viseven.develop.passwordprotectionsdk.result_handler.access_granted;

import com.viseven.develop.passwordprotectionsdk.result_handler.PasswordProtectionResultHandler;

public class AccessGrantedPasswordProtectionResultHandlerDecorator implements PasswordProtectionResultHandler {
	private PasswordProtectionResultHandler passwordProtectionResultHandler;
	private AccessGrantedListener accessGrantedListener;

	public AccessGrantedPasswordProtectionResultHandlerDecorator(
			PasswordProtectionResultHandler passwordProtectionResultHandler,
			AccessGrantedListener accessGrantedListener) {
		this.passwordProtectionResultHandler = passwordProtectionResultHandler;
		this.accessGrantedListener = accessGrantedListener;
	}

	@Override
	public void onAccessGranted() {
		this.passwordProtectionResultHandler.onAccessGranted();
		this.accessGrantedListener.accessGranted();
	}

	@Override
	public void onAccessDenied() {
		this.passwordProtectionResultHandler.onAccessDenied();
	}
}
