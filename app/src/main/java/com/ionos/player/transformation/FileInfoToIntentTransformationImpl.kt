package com.ionos.player.transformation

import android.content.Context
import android.content.Intent
import com.ionos.player.activity.IonosPlayerActivity
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.predicate.IsVideoPredicate
import com.strato.hidrive.player.transformation.FileInfoToIntentTransformation
import javax.inject.Inject

class FileInfoToIntentTransformationImpl @Inject constructor(
    private val context: Context,
    private val isVideoPredicate: IsVideoPredicate,
) : FileInfoToIntentTransformation {

    override fun transform(from: PlayerFileInfo): Intent {
        return if (isVideoPredicate.satisfied(from))
            IonosPlayerActivity.createVideoPlayerIntent(context)
        else IonosPlayerActivity.createAudioPlayerIntent(context)
    }

}