package com.ionos.player.transformation

import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToStringSizeTransformation
import javax.inject.Inject

class FileInfoToStringSizeTransformationImpl @Inject constructor(
    private val transformation: PlayerFileInfoToOCFileTransformation,
): FileInfoToStringSizeTransformation {
    override fun transform(from: PlayerFileInfo): String {
        return transformation.transform(from)
            ?.let { "${it.fileLength} b" }
            ?: ""
    }
}