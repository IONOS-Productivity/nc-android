package com.ionos.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToDisplayNameTransformation
import javax.inject.Inject

class FileInfoToDisplayNameTransformationImpl @Inject constructor(): FileInfoToDisplayNameTransformation {
    override fun transform(from: PlayerFileInfo): String {
        TODO("Not yet implemented")
    }
}