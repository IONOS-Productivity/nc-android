package com.ionos.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToMimetypeTransformation
import javax.inject.Inject

class FileInfoToMimetypeTransformationImpl @Inject constructor(): FileInfoToMimetypeTransformation {
    override fun transform(from: PlayerFileInfo): String? {
        TODO("Not yet implemented")
    }
}