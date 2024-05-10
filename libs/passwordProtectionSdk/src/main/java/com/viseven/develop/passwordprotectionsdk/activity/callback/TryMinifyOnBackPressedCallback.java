package com.viseven.develop.passwordprotectionsdk.activity.callback;

import android.app.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;

public class TryMinifyOnBackPressedCallback extends OnBackPressedCallback {

	private final Activity activity;

	public TryMinifyOnBackPressedCallback(@NonNull Activity activity) {
		super(true);
		this.activity = activity;
	}

	@Override
	public void handleOnBackPressed() {
		try {
			if (!activity.moveTaskToBack(true)) {
				activity.finishAffinity();
			}
		} catch (Exception e) {
		}
	}
}