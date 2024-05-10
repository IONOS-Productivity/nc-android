package com.strato.hidrive.common_ui.dialog.stylized.overwrite_file_dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.strato.hidrive.domain.interfaces.actions.ParamAction;

import java.util.List;
import java.util.Queue;

public class OverwriteCancelDialogListener<T> implements OverwriteCancelListener {
	private final Queue<T> entitiesForDialogsQueue;
	protected final List<T> entitiesToBeOverwritten;

	private final DialogsCounter dialogsCounter;
	private final T entityForOverride;

	@Nullable
	private final ParamAction<T> onEachSingleDialogClosed;

	public OverwriteCancelDialogListener(@NonNull Queue<T> entitiesForDialogsQueue,
										 @NonNull List<T> entitiesToBeOverwritten,
										 @NonNull DialogsCounter dialogsCounter,
										 @NonNull T entityForOverride,
										 @Nullable ParamAction<T> onEachSingleDialogClosed) {
		this.entitiesForDialogsQueue = entitiesForDialogsQueue;
		this.entitiesToBeOverwritten = entitiesToBeOverwritten;
		this.dialogsCounter = dialogsCounter;
		this.entityForOverride = entityForOverride;
		this.onEachSingleDialogClosed = onEachSingleDialogClosed;
	}

	@Override
	public void onCancelClicked(boolean applyToAll) {
		if (applyToAll) {
			if (onEachSingleDialogClosed != null) {
				for (T remaining : entitiesForDialogsQueue) {
					onEachSingleDialogClosed.execute(remaining);
				}
			}
			entitiesForDialogsQueue.clear();
			dialogsCounter.handleAllDialogClosed();
		} else {
			if (onEachSingleDialogClosed != null) {
				onEachSingleDialogClosed.execute(entityForOverride);
			}
			dialogsCounter.handleDialogClosed();
		}
	}

	@Override
	public void onOverwriteClicked(boolean applyToAll) {
		entitiesToBeOverwritten.add(entityForOverride);
		if (applyToAll) {
			entitiesToBeOverwritten.addAll(entitiesForDialogsQueue);
			entitiesForDialogsQueue.clear();
			dialogsCounter.handleAllDialogClosed();
		} else {
			dialogsCounter.handleDialogClosed();
		}
	}
}
