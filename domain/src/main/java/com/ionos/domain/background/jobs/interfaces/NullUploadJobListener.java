package com.ionos.domain.background.jobs.interfaces;

import com.ionos.domain.entity.RemoteFileInfo;

/**
 * Created by Sergey Shandyuk on 12/18/2017.
 */

public class NullUploadJobListener implements UploadJobListener {
	public final static UploadJobListener INSTANCE = new NullUploadJobListener();

	private NullUploadJobListener() {
	}

	@Override
	public void onUploadFileFail(Throwable error) {

	}

	@Override
	public void onUploadFileSuccess(String localFilePath, RemoteFileInfo uploadedFile) {

	}

	@Override
	public void onUploadComplete() {

	}
}
