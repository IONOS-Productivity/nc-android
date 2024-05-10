package com.strato.hidrive.domain.predicate.file_info.filter

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.predicate.file_info.FileInfoPredicate
import com.strato.hidrive.domain.provider.HidrivePathProvider
import com.strato.hidrive.domain.utils.FileUtils

fun interface FilterTeamFolderPredicateFactory {
	fun create(parentPath: String?): FilterTeamFolderPredicate
}

class FilterTeamFolderPredicate(
	private val parentPath: String?,
	private val pathProvider: HidrivePathProvider
) : FileInfoPredicate {

	override fun satisfied(value: RemoteFileInfo): Boolean {
		return value.isTeamfolder ||
				getParentPath(value.path) != pathProvider.teamfolderPath
	}

	private fun getParentPath(childPath: String) =
		parentPath ?: FileUtils.getParentDirPath(childPath)
}