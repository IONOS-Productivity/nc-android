package com.ionos.common_ui.dialog.stylized.overwrite_file_dialog;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ionos.common_ui.dialog.stylized.StylizedDialog;
import com.ionos.domain.interfaces.actions.ParamAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import io.reactivex.Maybe;

/**
 * Created by dmitrij on 1/13/17.
 */

public abstract class OverwriteDialogs<T> {
	protected final List<T> entitiesForOverride = new ArrayList<>();
	protected final Queue<T> entitiesForDialogsQueue = new LinkedBlockingDeque<>();
	protected final DialogsCounter dialogsCounter;
	protected final OverwriteQueueListener<T> queueListener;
	protected final Activity activity;
	@Nullable
	private StylizedDialog<?> currentlyShownDialog;
	@Nullable
	protected final ParamAction<T> onEachSingleDialogClosed;


	public OverwriteDialogs(@NonNull List<T> entitiesToProceed,
							@NonNull Activity activity,
							@Nullable ParamAction<T> onEachSingleDialogClosed) {
		this.activity = activity;
		this.entitiesForDialogsQueue.addAll(entitiesToProceed);
		this.queueListener = new OverwriteQueueListener<>(entitiesForDialogsQueue);
		this.dialogsCounter = new DialogsCounter(entitiesToProceed.size(), queueListener);
		this.onEachSingleDialogClosed = onEachSingleDialogClosed;
	}

	@NonNull
	public Maybe<List<T>> filterOverwriteFiles() {
		return queueListener.getShowOverrideDialogEvent()
				.doOnNext(this::showNextDialog)
				.ignoreElements()
				.andThen(Maybe.just(entitiesForOverride))
				.doOnDispose(() -> {
					if (currentlyShownDialog != null) currentlyShownDialog.dismiss();
				});
	}

	private void showNextDialog(@NonNull T nextEntity) {
		if (activity != null && !(activity.isFinishing() || activity.isDestroyed())) {
			DialogWrapperCreator<T> dialogWrapperCreator = getWrapperCreator();
			currentlyShownDialog = dialogWrapperCreator
					.createDialogWrapper(nextEntity, entitiesForDialogsQueue.isEmpty())
					.show();
		}
	}

	@NonNull
	protected abstract DialogWrapperCreator<T> getWrapperCreator();
}
