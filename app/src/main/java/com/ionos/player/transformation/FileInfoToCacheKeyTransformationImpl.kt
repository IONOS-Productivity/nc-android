package com.ionos.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToCacheKeyTransformation
import javax.inject.Inject

class FileInfoToCacheKeyTransformationImpl @Inject constructor(): FileInfoToCacheKeyTransformation {
    override fun transform(from: PlayerFileInfo): String {
        return from.toString()
    }
}