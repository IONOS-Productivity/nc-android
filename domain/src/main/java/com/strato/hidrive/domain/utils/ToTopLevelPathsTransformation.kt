package com.strato.hidrive.domain.utils

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.interfaces.actions.TransformationRx
import io.reactivex.Single
import javax.inject.Inject

/**
 * Converts list of fileInfos to list of paths,
 * such that each path is a parent path to some of source fileInfo (or equals to it if fileInfo is a directory).
 */
class ToTopLevelPathsTransformation @Inject constructor() :
	TransformationRx<List<RemoteFileInfo>, List<String>> {

	override fun transform(from: List<RemoteFileInfo>): Single<List<String>> {
		return Single.just(from)
			.map(::uniquePaths)
			.map(::uniqueOrderedPaths)
			.map(::topLevelPaths)
	}

	private fun topLevelPaths(paths: List<String>): List<String> {
		val mutableSet = mutableSetOf<String>()

		paths.forEach { candidate ->
			if (mutableSet.none { candidate.startsWith(it) }) mutableSet.add(candidate)
		}

		return mutableSet.toList()
	}

	private fun uniqueOrderedPaths(path: Set<String>): List<String> =
		path
			.sortedBy {
				it.split(FileUtils.PATH_SEPARATOR)
					.count()
			}

	private fun uniquePaths(files: List<RemoteFileInfo>): Set<String> =
		files
			.map {
				if (it.isDirectory) it.path
				else FileUtils.getParentDirPath(it.path)
			}
			.toSet()
}