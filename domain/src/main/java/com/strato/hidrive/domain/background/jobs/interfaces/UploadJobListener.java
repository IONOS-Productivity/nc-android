package com.strato.hidrive.domain.background.jobs.interfaces;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

/**
 * Created by Alex Kucherenko on 14.03.2016.
 */
public interface UploadJobListener {
	void onUploadFileFail(Throwable error);

	void onUploadFileSuccess(String localFilePath, RemoteFileInfo uploadedFile);

	void onUploadComplete();
}
