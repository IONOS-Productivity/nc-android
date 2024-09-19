package com.ionos.player.transformation

import android.content.Intent
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToIntentTransformation
import javax.inject.Inject

class FileInfoToIntentTransformationImpl @Inject constructor(): FileInfoToIntentTransformation {

    override fun transform(from: PlayerFileInfo): Intent {
        TODO("Not yet implemented")
    }

}