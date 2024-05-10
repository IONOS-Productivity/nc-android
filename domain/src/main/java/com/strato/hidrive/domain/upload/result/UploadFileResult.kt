package com.strato.hidrive.domain.upload.result

import com.strato.hidrive.domain.entity.RemoteFileInfo

sealed interface UploadFileResult {

	data class Success(
		val fileInfo: RemoteFileInfo
	) : UploadFileResult

	data class Error(
		val throwable: Throwable
	) : UploadFileResult

}