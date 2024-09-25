package com.ionos.common_ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ionos.common_ui.R;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

public class LockProgressDialog {

    private AlertDialog dialog;

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
            View view = activity.getLayoutInflater().inflate(R.layout.lock_progress_dialog, null);
            TextView progressTextView = view.findViewById(R.id.progress_text);
            progressTextView.setText(message);

            this.dialog = new MaterialAlertDialogBuilder(activity)
                .setView(view)
                .create();

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
