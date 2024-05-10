package com.strato.hidrive.domain.predicate.file_info

import com.strato.hidrive.domain.entity.RemoteFileInfo
import javax.inject.Inject

class HiddenFilePredicate @Inject constructor() : FileInfoPredicate {

	override fun satisfied(fileInfo: RemoteFileInfo): Boolean {
		return fileInfo.name.startsWith(".")
	}
}