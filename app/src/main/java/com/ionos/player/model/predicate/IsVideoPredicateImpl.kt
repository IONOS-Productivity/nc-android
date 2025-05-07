package com.ionos.player.model.predicate

import com.ionos.player.model.PlayerFileInfo
import com.owncloud.android.utils.MimeTypeUtil
import javax.inject.Inject

class IsVideoPredicateImpl @Inject constructor(): IsVideoPredicate {

    override fun satisfied(value: PlayerFileInfo): Boolean {
        return MimeTypeUtil.isVideo(value.mimeType)
    }

}