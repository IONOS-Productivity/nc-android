@file:JvmName("FileInfoMerger")

package com.strato.hidrive.domain.manager.merger

import com.strato.hidrive.domain.entity.RemoteFileInfo

@JvmName("merge")
fun RemoteFileInfo.mergeWith(info: RemoteFileInfo): RemoteFileInfo = copy(
	id = info.id ?: this.id,
	path = info.path.ifBlank { this.path },
	name = info.name.ifBlank { this.name },
	isDirectory = info.isDirectory,
	lastModified = info.lastModified,
	creationTime = info.creationTime,
	contentLength = info.contentLength,
	isReadable = info.isReadable,
	isWritable = info.isWritable,
	isTeamfolder = info.isTeamfolder,
	membersCount = info.membersCount,
	mHash = info.mHash,
	imageInfo = info.imageInfo ?: this.imageInfo,
	decodedName = mergeDecodedName(info),
	exportedDirectoryKey = info.exportedDirectoryKey ?: this.exportedDirectoryKey,
	directoryKey = info.directoryKey ?: this.directoryKey,
	parentId = info.parentId ?: this.parentId,
)

private fun RemoteFileInfo.mergeDecodedName(info: RemoteFileInfo): String? = when {
	info.hasDecodedName() -> info.getDecodedName()
	this.hasDecodedName() -> this.getDecodedName()
	else -> null
}
