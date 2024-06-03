package com.strato.hidrive.scanbot.upload

import android.net.Uri
import com.ionos.domain.background.jobs.interfaces.UploadJobListener
import com.ionos.domain.entity.RemoteFileInfo
import com.ionos.domain.interfaces.actions.ParamAction
import com.ionos.domain.optional.Function
import com.ionos.domain.optional.Optional

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
