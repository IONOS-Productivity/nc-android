package com.ionos.player.predicate

import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import com.owncloud.android.utils.MimeTypeUtil
import com.ionos.player.domain.PlayerFileInfo
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