package com.strato.hidrive.domain.background.jobs.factory

import com.strato.hidrive.domain.background.jobs.BaseBackgroundJob
import com.strato.hidrive.domain.background.jobs.JobType
import com.strato.hidrive.domain.background.jobs.interfaces.UploadJobListener
import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.optional.Optional
import java.io.File

interface UploadJobWithOverwriteFactory {
	fun create(
		file: File,
		uploadDirPath: String,
		uploadDir: Optional<RemoteFileInfo>,
		uploadJobListener: UploadJobListener?,
		jobType: JobType,
	): BaseBackgroundJob
}
