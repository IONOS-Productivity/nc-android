package com.ionos.domain.upload.result

import com.ionos.domain.entity.RemoteFileInfo

sealed interface UploadFileResult {

	data class Success(
		val fileInfo: RemoteFileInfo
	) : UploadFileResult

	data class Error(
		val throwable: Throwable
	) : UploadFileResult

}