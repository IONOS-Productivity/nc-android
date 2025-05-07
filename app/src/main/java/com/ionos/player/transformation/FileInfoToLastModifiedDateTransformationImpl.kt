package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
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