package com.ionos.player.transformation

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class FileInfoToDisplayNameTransformationImpl @Inject constructor(): FileInfoToDisplayNameTransformation {
    override fun transform(from: PlayerFileInfo): String {
        return from.id
    }
}