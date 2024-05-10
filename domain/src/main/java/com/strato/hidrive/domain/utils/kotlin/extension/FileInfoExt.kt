@file:JvmName("FileInfoUtils")

package com.strato.hidrive.domain.utils.kotlin.extension

import com.strato.hidrive.domain.entity.RemoteDir
import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.manager.interfaces.ICachedRemoteFileMgr
import com.strato.hidrive.domain.predicate.Predicate
import com.strato.hidrive.domain.provider.HidrivePathUtils
import com.strato.hidrive.domain.utils.FileUtils
import java.io.File

/**
 * Created by: Denis Botvin
 * Date: 23.10.2019
 */

fun RemoteFileInfo.getDecodedPath(cachedRemoteFileMgr: ICachedRemoteFileMgr): String =
	cachedRemoteFileMgr
		.blockingFindParentFileInfo(this)
		?.let { parent -> parent.getDecodedPath(cachedRemoteFileMgr) + "/" + getDecodedName() }
		?: getDecodedName()

fun RemoteFileInfo.getFullPathWithDecodedFileName(): String =
	FileUtils.correctPath(FileUtils.getParentDirPath(path)) + getDecodedName()

fun RemoteFileInfo.isChildOf(pathUtils: HidrivePathUtils, parent: RemoteFileInfo) =
	isChildOf(pathUtils, path, parent.path)

private fun isChildOf(pathUtils: HidrivePathUtils, path: String, testedParent: String): Boolean {
	val parentPath = pathUtils.getParentPath(path)
	if (parentPath == null || !parentPath.contains(File.separator)) return false
	return parentPath == testedParent || isChildOf(pathUtils, parentPath, testedParent)
}

fun RemoteDir.filterChildren(filterPredicate: Predicate<RemoteFileInfo>): RemoteDir {
	val filtered = children.filter(filterPredicate::satisfied)
	return copy(children = filtered)
}

fun RemoteDir.sortChildren(comparator: Comparator<RemoteFileInfo>): RemoteDir {
	val sorted = children.sortedWith(comparator)
	return copy(children = sorted)
}
