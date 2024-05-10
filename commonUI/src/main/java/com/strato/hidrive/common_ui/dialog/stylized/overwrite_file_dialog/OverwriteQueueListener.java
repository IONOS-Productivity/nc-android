package com.strato.hidrive.common_ui.dialog.stylized.overwrite_file_dialog;

import androidx.annotation.NonNull;

import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class OverwriteQueueListener<T> implements DialogsCounter.DialogsCounterListener {
	private final PublishSubject<T> showOverrideDialogEvent = PublishSubject.create();

	private final Queue<T> dialogWrapperQueue;

	public OverwriteQueueListener(@NonNull Queue<T> dialogsQueue) {
		this.dialogWrapperQueue = dialogsQueue;
	}

	@Override
	public void onSingleDialogClosed() {
		T next = dialogWrapperQueue.poll();
		if (next != null) showOverrideDialogEvent.onNext(next);
	}

	@Override
	public void onAllDialogsClosed() {
		showOverrideDialogEvent.onComplete();
	}

	@NonNull
	public Observable<T> getShowOverrideDialogEvent() {
		T firstElement = dialogWrapperQueue.poll();
		Observable<T> start;
		if (firstElement == null) start = Observable.empty();
		else start = Observable.just(firstElement);

		return showOverrideDialogEvent
				.startWith(start);
	}
}
