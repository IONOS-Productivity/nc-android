package com.ionos.player.model.predicate

import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import com.owncloud.android.utils.MimeTypeUtil
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