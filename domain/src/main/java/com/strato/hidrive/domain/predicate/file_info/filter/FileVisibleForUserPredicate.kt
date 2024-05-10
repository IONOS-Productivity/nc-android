package com.strato.hidrive.domain.predicate.file_info.filter

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.predicate.CompositePredicate
import com.strato.hidrive.domain.predicate.file_info.FileInfoPredicate

fun interface FileVisibleForUserPredicateFactory {
	fun create(parentPath: String?): FileVisibleForUserPredicate
}

class FileVisibleForUserPredicate(
	parentPath: String?,
	filterReadableFilePredicate: FilterReadableFilePredicate,
	filterHiddenFilePredicate: FilterHiddenFilePredicate,
	filterEncryptedSystemFilePredicate: FilterEncryptedSystemFilePredicate,
	filterTeamFolderPredicate: FilterTeamFolderPredicateFactory,
	filterUsersFolderPredicate: FilterUsersFolderPredicate,
) : FileInfoPredicate {

	private val compositePredicate = CompositePredicate(
		filterReadableFilePredicate,
		filterHiddenFilePredicate,
		filterEncryptedSystemFilePredicate,
		filterTeamFolderPredicate.create(parentPath),
		filterUsersFolderPredicate,
	)

	override fun satisfied(value: RemoteFileInfo): Boolean {
		return compositePredicate.satisfied(value)
	}

}