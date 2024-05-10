package com.strato.hidrive.domain.predicate.file_info.filter

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.predicate.file_info.FileInfoPredicate
import javax.inject.Inject

class FilterReadableFilePredicate @Inject constructor(): FileInfoPredicate {

	override fun satisfied(value: RemoteFileInfo): Boolean {
		return value.isReadable
	}

}