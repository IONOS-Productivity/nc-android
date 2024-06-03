package com.ionos.domain.background.jobs;


import androidx.annotation.Nullable;

import com.ionos.domain.background.jobs.interfaces.BackgroundJobActivityListener;
import com.strato.hidrive.domain.logger.LoggerUtil;

import io.reactivex.Completable;

public abstract class BaseBackgroundJob {

	private volatile BackgroundJobActivityListener activityListener;

	protected boolean isCanceled = false;


	public abstract void start();

	protected abstract void cancelLoading();

	protected abstract Completable cancelLoadingCompletable();

	protected abstract boolean isEqual(Object obj);

	protected abstract int calculateHashCode();

	public void setActivityListener(BackgroundJobActivityListener listener) {
		this.activityListener = listener;
	}

	public BackgroundJobActivityListener getActivityListener() {
		return activityListener;
	}

	abstract public BackgroundJobType getType();

	public final void cancel() {
		this.isCanceled = true;
		cancelLoading();
		this.activityListener = null;
	}

	public final Completable cancelJobCompletable() {
		isCanceled = true;
		return cancelLoadingCompletable()
				.doOnComplete(() -> activityListener = null)
				.doOnError(throwable -> activityListener = null)
				.onErrorComplete();
	}

	protected final void onSuccess() {
		BackgroundJobActivityListener activityListener = getActivityListener();
		if (activityListener != null) {
			LoggerUtil.logD("uploadProgress", "run: BaseBackgroundJob onJobSuccess ");
			activityListener.onJobSuccess(BaseBackgroundJob.this);
		}
	}

	protected final void onFail(final Throwable error) {
		BackgroundJobActivityListener activityListener = getActivityListener();
		if (activityListener != null) {
			LoggerUtil.logD("uploadProgress", "run: BaseBackgroundJob onFail " + error.getMessage());
			activityListener.onJobFail(BaseBackgroundJob.this, error);
		}
	}

	protected final void onProgress() {
		BackgroundJobActivityListener activityListener = getActivityListener();
		if (activityListener != null) {
			LoggerUtil.logD("uploadProgress", "run: BaseBackgroundJob onProgress");
			activityListener.onJobProgressChanged(BaseBackgroundJob.this);
		}
	}

	protected final void onProgress(final long currentProgress, final long totalSize) {
		BackgroundJobActivityListener activityListener = getActivityListener();
		if (activityListener != null) {
			activityListener.onJobProgressChanged(BaseBackgroundJob.this, currentProgress, totalSize);
		}
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		return isEqual(obj);
	}

	@Override
	public int hashCode() {
		return calculateHashCode();
	}
}
