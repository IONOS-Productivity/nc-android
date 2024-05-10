package com.strato.hidrive.scanbot.screens.save.use_case

import com.strato.hidrive.domain.api.bll.file.transformation.public_file.TeamfolderToLocalFilePathTransformation
import com.strato.hidrive.domain.api.bll.file.transformation.remote_file.RemoteToLocalFilePathTransformation
import com.strato.hidrive.domain.manager.interfaces.ICachedRemoteFileMgr
import com.strato.hidrive.domain.upload.target.UploadTarget
import com.strato.hidrive.domain.utils.kotlin.extension.getDecodedPath
import com.strato.hidrive.scanbot.exception.CachedFileNotFoundException
import io.reactivex.Single
import javax.inject.Inject

internal class GetTargetPath @Inject constructor(
	private val cachedRemoteFileMgr: ICachedRemoteFileMgr,
	private val remoteToLocalFilePathTransformation: RemoteToLocalFilePathTransformation,
	private val teamFolderToLocalFilePathTransformation: TeamfolderToLocalFilePathTransformation,
) {

	operator fun invoke(uploadTarget: UploadTarget): Single<String> = cachedRemoteFileMgr
		.getFileFromCache(uploadTarget.remotePath)
		.map { it.orNull() ?: throw CachedFileNotFoundException(uploadTarget.remotePath) }
		.map { getTargetPath(uploadTarget, it.getDecodedPath(cachedRemoteFileMgr)) }

	private fun getTargetPath(uploadTarget: UploadTarget, decodedPath: String): String = when (uploadTarget) {
		is UploadTarget.RemotePath -> remoteToLocalFilePathTransformation.transform(decodedPath)
		is UploadTarget.PublicPath -> teamFolderToLocalFilePathTransformation.transform(decodedPath)
	}
}
