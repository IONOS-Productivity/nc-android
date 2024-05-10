package com.strato.hidrive.common_ui.dialog;

import android.app.Activity;
import android.app.ProgressDialog;

import androidx.annotation.StringRes;

public class LockProgressDialog {

	private ProgressDialog dialog;

	public void start(Activity activity, @StringRes int messageRes) {
		showProgressDialog(activity, activity.getString(messageRes));
	}

	public void start(Activity activity, String message) {
		showProgressDialog(activity, message);
	}

	private void showProgressDialog(Activity activity, String message) {
		if (isShowing()) {
			stop();
		}

		if (!activity.isFinishing() && !activity.isDestroyed()) {
			this.dialog = new SafeProgressDialog(activity);
			this.dialog.setTitle("");
			this.dialog.setMessage(message);
			this.dialog.setIndeterminate(true);
			this.dialog.setCancelable(false);
			this.dialog.show();
		}
	}

	public boolean isShowing() {
		return this.dialog != null && this.dialog.isShowing();
	}

	public void stop() {
		if (this.dialog != null) {
			this.dialog.dismiss();
		}
	}
}
