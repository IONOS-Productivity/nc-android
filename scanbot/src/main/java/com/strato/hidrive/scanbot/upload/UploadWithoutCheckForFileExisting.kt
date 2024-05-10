package com.strato.hidrive.scanbot.upload

import android.net.Uri
import com.strato.hidrive.domain.background.jobs.interfaces.UploadJobListener
import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.interfaces.actions.ParamAction
import com.strato.hidrive.domain.optional.Function
import com.strato.hidrive.domain.optional.Optional

interface UploadWithoutCheckForFileExisting {

	@JvmSuppressWildcards
	fun startUploadWithoutCheckForFileExisting(
		uris: List<Uri>,
		uploadDirPath: String,
		uploadDir: Optional<RemoteFileInfo>,
		createUploadListener: Function<Uri, UploadJobListener>,
		allFilesHaveBeenProcessed: ParamAction<Int>,
	)
}
