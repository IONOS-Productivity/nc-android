package com.ionos.domain.background.jobs.interfaces;

import com.ionos.domain.entity.RemoteFileInfo;

/**
 * Created by Alex Kucherenko on 14.03.2016.
 */
public interface UploadJobListener {
	void onUploadFileFail(Throwable error);

	void onUploadFileSuccess(String localFilePath, RemoteFileInfo uploadedFile);

	void onUploadComplete();
}
