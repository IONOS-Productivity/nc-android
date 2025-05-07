package com.ionos.player.transformation

import android.net.Uri
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import javax.inject.Inject

class FileInfoToUriTransformationImpl @Inject constructor(
    private val transformationImpl: PlayerFileInfoToOCFileTransformation,
) : FileInfoToUriTransformation {
    override fun transform(from: PlayerFileInfo): Uri {
        return transformationImpl.transform(from)
            ?.let { "player://${it.remotePath}" }
            ?.let { Uri.parse(it) }
            ?: Uri.parse("")
    }
}