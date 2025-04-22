package com.ionos.player.transformation

import com.ionos.player.domain.PlayerFileInfo
import javax.inject.Inject

class FileInfoToDisplayNameTransformationImpl @Inject constructor(): FileInfoToDisplayNameTransformation {
    override fun transform(from: PlayerFileInfo): String {
        return from.id
    }
}