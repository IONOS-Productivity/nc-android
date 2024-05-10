package com.strato.hidrive.domain.predicate.path

import com.strato.hidrive.domain.provider.HidrivePathProvider
import javax.inject.Inject

class SystemPathPredicate @Inject constructor(
	private val pathProvider: HidrivePathProvider
) : PathPredicate {

	override fun satisfied(remoteFileInfoPath: String): Boolean {
		val rootFolder = pathProvider.cacheRootFolderName
		val systemFolderRegex = Regex("^$rootFolder/[^/]+$") // path like root/public or root/users
		return remoteFileInfoPath == pathProvider.currentUserFolderPath ||
				systemFolderRegex.matches(remoteFileInfoPath)
	}
}