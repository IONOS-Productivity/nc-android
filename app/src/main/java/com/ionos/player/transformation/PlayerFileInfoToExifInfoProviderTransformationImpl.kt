package com.ionos.player.transformation

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.PlayerFileInfoToExifInfoProviderTransformation
import com.strato.hidrive.views.exif_info.ExifInfoProvider
import javax.inject.Inject

class PlayerFileInfoToExifInfoProviderTransformationImpl @Inject constructor(): PlayerFileInfoToExifInfoProviderTransformation {
    override fun transform(from: PlayerFileInfo): ExifInfoProvider {
        TODO("Not yet implemented")
    }
}