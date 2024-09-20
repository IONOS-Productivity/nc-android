package com.ionos.player.transformation

import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToLastModifiedDateTransformation
import javax.inject.Inject

class FileInfoToLastModifiedDateTransformationImpl @Inject constructor(
    private val transformation: PlayerFileInfoToOCFileTransformation,
): FileInfoToLastModifiedDateTransformation {

    override fun transform(from: PlayerFileInfo): String {
        return transformation.transform(from)
            ?.let { "${it.modificationTimestamp} millis" }
            ?: ""
    }

}