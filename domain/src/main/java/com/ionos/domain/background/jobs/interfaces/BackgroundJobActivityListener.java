package com.ionos.domain.background.jobs.interfaces;

import com.ionos.domain.background.jobs.BaseBackgroundJob;

public interface BackgroundJobActivityListener {
	void onJobSuccess(BaseBackgroundJob job);

	void onJobFail(BaseBackgroundJob job, Throwable error);

	void onJobProgressChanged(BaseBackgroundJob job);

	void onJobProgressChanged(BaseBackgroundJob job, long current, long total);
}