package com.viseven.develop.passwordprotectionsdk;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viseven.develop.passwordprotectionsdk.activity.callback.TryMinifyOnBackPressedCallback;
import com.viseven.develop.passwordprotectionsdk.result_handler.PasswordProtectionResultHandler;

public class PasswordProtectionActivity
		extends AppCompatActivity
		implements PasswordProtectionResultHandler {

	public static final int REQUEST_CODE = 123;
	private final PasswordProtectionSdk passwordProtectionSdk = PasswordProtectionSdk.getInstance();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullscreenMode();
		setContentView(R.layout.activity_password_protection);

		getOnBackPressedDispatcher().addCallback(this, new TryMinifyOnBackPressedCallback(this));

		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_container, this.passwordProtectionSdk.getConfiguration().fragmentFactory.create())
				.commit();
	}

	private void setFullscreenMode() {
		Window window = getWindow();
		window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		window.addFlags(FLAG_FULLSCREEN);
	}

	@Override
	public void onAccessGranted() {
		this.passwordProtectionSdk
				.getConfiguration()
				.passwordProtectionResultHandler
				.onAccessGranted();
		finish();
	}

	@Override
	public void onAccessDenied() {
		this.passwordProtectionSdk
				.getConfiguration()
				.passwordProtectionResultHandler
				.onAccessDenied();
		finish();
	}
}
