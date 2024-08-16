package com.ionos.common_ui.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.ionos.common_ui.utils.ContextUtils;
import com.ionos.common_ui.di.CommonUiComponent;
import com.ionos.common_ui.exception.TryCatchExceptionHandler;

import javax.inject.Inject;

import androidx.annotation.StyleRes;

public final class SafeProgressDialog extends ProgressDialog {

	@Inject
	TryCatchExceptionHandler tryCatchExceptionHandler;

	public SafeProgressDialog(Context context) {
		super(context);
		CommonUiComponent.from(context).inject(this);
	}

    public SafeProgressDialog(Context context, @StyleRes int theme) {
        super(context, theme);
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
