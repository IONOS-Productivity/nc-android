package com.ionos.player.model.predicate

import com.ionos.player.model.PlaybackFile
import com.owncloud.android.utils.MimeTypeUtil
import javax.inject.Inject

class IsVideoPredicateImpl @Inject constructor(): IsVideoPredicate {

    override fun satisfied(file: PlaybackFile): Boolean {
        return MimeTypeUtil.isVideo(file.mimeType)
    }

}