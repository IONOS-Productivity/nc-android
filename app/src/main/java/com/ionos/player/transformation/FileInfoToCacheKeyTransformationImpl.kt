package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class FileInfoToCacheKeyTransformationImpl @Inject constructor(): FileInfoToCacheKeyTransformation {
    override fun transform(from: PlayerFileInfo): String {
        return from.toString()
    }
}