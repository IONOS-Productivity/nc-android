package com.ionos.player.transformation

import android.content.Context
import android.content.Intent
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.predicate.IsVideoPredicate
import com.ionos.player.ui.IonosPlayerActivity
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