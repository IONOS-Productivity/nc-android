package com.ionos.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToStringSizeTransformation
import javax.inject.Inject

class FileInfoToStringSizeTransformationImpl @Inject constructor(): FileInfoToStringSizeTransformation {
    override fun transform(from: PlayerFileInfo): String {
        TODO("Not yet implemented")
    }
}