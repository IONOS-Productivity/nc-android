package com.strato.hidrive.common_ui.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.strato.hidrive.common_ui.di.CommonUiComponent;
import com.strato.hidrive.domain.exception.TryCatchExceptionHandler;
import com.strato.hidrive.domain.interfaces.actions.ThrowableAction;
import com.strato.hidrive.common_ui.utils.ContextUtils;

import javax.inject.Inject;

public final class SafeProgressDialog extends ProgressDialog {

	@Inject
	TryCatchExceptionHandler tryCatchExceptionHandler;

	public SafeProgressDialog(Context context) {
		super(context);
		CommonUiComponent.from(context).inject(this);
	}

	@Override
	public void show() {
		if (!isShowing()) {
			Activity activity = ContextUtils.getActivity(getContext());
			if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
				this.tryCatchExceptionHandler.handle(SafeProgressDialog.super::show);
			}
		}
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			Activity activity = ContextUtils.getActivity(getContext());
			if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
				this.tryCatchExceptionHandler.handle(SafeProgressDialog.super::dismiss);
			}
		}
	}
}
