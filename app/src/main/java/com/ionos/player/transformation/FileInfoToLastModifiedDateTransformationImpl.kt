package com.ionos.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToLastModifiedDateTransformation
import javax.inject.Inject

class FileInfoToLastModifiedDateTransformationImpl @Inject constructor(): FileInfoToLastModifiedDateTransformation {
    override fun transform(from: PlayerFileInfo): String {
        TODO("Not yet implemented")
    }
}