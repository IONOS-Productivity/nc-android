package com.ionos.common_ui.dialog.stylized.overwrite_file_dialog;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ionos.common_ui.dialog.wrapper.OverwriteFileDialogWrapper;
import com.ionos.domain.interfaces.actions.ParamAction;

import java.util.List;
import java.util.Queue;

public abstract class DialogWrapperCreator<T> {

	protected final Activity activity;
	private final DialogsCounter dialogsCounter;
	private final List<T> entitiesForOverride;
	private final Queue<T> entitiesForDialogsQueue;
	@Nullable
	private final ParamAction<T> onEachSingleDialogClosed;

	public DialogWrapperCreator(@NonNull Activity activity,
								@NonNull DialogsCounter dialogsCounter,
								@NonNull List<T> entitiesForOverride,
								@NonNull Queue<T> entitiesForDialogsQueue,
								@Nullable ParamAction<T> onEachSingleDialogClosed) {
		this.activity = activity;
		this.dialogsCounter = dialogsCounter;
		this.entitiesForOverride = entitiesForOverride;
		this.entitiesForDialogsQueue = entitiesForDialogsQueue;
		this.onEachSingleDialogClosed = onEachSingleDialogClosed;
	}

	@NonNull
	public OverwriteFileDialogWrapper createDialogWrapper(@NonNull T entityForOverride,
                                                          boolean lastInQueue) {
		return new OverwriteFileDialogWrapper(
				activity,
				getFileName(entityForOverride),
				getDialogClickListener(entityForOverride),
				lastInQueue);
	}

	@NonNull
	private OverwriteCancelListener getDialogClickListener(@NonNull T entityForOverride) {
		return new OverwriteCancelDialogListener<>(
				entitiesForDialogsQueue, entitiesForOverride,
				dialogsCounter, entityForOverride, onEachSingleDialogClosed);
	}

	@NonNull
	protected abstract String getFileName(@NonNull T entityForOverride);
}
