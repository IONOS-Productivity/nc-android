package com.ionos.player.predicate

import com.ionos.player.transformation.player_file_info.PlayerFileInfoToOCFileTransformation
import com.owncloud.android.utils.MimeTypeUtil
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.predicate.IsVideoPredicate
import javax.inject.Inject

class IsVideoPredicateImpl @Inject constructor(
    private val transformation: PlayerFileInfoToOCFileTransformation,
): IsVideoPredicate {

    override fun satisfied(value: PlayerFileInfo): Boolean {
        return  transformation.transform(value)
            ?.let(MimeTypeUtil::isVideo)
            ?: false
    }

}