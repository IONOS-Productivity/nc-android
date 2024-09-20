package com.ionos.player.transformation

import android.net.Uri
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToUriTransformation
import javax.inject.Inject

class FileInfoToUriTransformationImpl @Inject constructor(): FileInfoToUriTransformation {
    override fun transform(from: PlayerFileInfo): Uri {
        return Uri.parse("")
    }
}