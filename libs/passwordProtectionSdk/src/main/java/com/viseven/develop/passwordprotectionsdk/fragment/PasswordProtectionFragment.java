package com.viseven.develop.passwordprotectionsdk.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.viseven.develop.passwordprotectionsdk.result_handler.PasswordProtectionResultHandler;

public abstract class PasswordProtectionFragment extends Fragment {
	protected PasswordProtectionResultHandler passwordProtectionResultHandler;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		if (context instanceof PasswordProtectionResultHandler) {
			this.passwordProtectionResultHandler = (PasswordProtectionResultHandler) context;
		} else {
			throw new RuntimeException("Holder activity should implement PasswordProtectionResultHandler");
		}
	}
}
