package com.strato.hidrive.domain.exception

import com.strato.hidrive.domain.entity.RemoteFileInfo

class FilePathHasBeenChangedException @JvmOverloads constructor(
	val oldPath: String,
	val updatedFileInfo: RemoteFileInfo,
	message: String = "File path '$oldPath' has been changed to '${updatedFileInfo.path}'",
) : Exception(message)
