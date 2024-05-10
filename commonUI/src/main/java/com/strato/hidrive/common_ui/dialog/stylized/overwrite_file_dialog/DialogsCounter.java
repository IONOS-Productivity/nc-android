package com.strato.hidrive.common_ui.dialog.stylized.overwrite_file_dialog;

import androidx.annotation.NonNull;

/**
 * User: Dima Muravyov
 * Date: 10.11.2016
 */

public class DialogsCounter {

	public interface DialogsCounterListener {
		void onSingleDialogClosed();

		void onAllDialogsClosed();
	}

	private int displayedDialogsCount = 0;
	private final DialogsCounterListener dialogsCounterListener;

	public DialogsCounter(int displayedDialogsCount, @NonNull DialogsCounterListener dialogsCounterListener) {
		this.displayedDialogsCount = displayedDialogsCount;
		this.dialogsCounterListener = dialogsCounterListener;
	}

	public void handleAllDialogClosed(){
		this.displayedDialogsCount = 0;
		this.dialogsCounterListener.onAllDialogsClosed();
	}

	public void handleDialogClosed() {
		this.displayedDialogsCount--;
		if (this.displayedDialogsCount == 0) {
			this.dialogsCounterListener.onAllDialogsClosed();
		} else {
			this.dialogsCounterListener.onSingleDialogClosed();
		}
	}
}
