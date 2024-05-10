package com.viseven.develop.passwordprotectionsdk.activity.callback;

import androidx.activity.OnBackPressedCallback;

public class PreventClosingOnBackPressedCallback extends OnBackPressedCallback {

	public PreventClosingOnBackPressedCallback() {
		super(true);
	}

	@Override
	public void handleOnBackPressed() {
	}
}
