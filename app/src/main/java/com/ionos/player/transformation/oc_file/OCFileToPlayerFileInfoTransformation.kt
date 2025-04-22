package com.ionos.player.transformation.oc_file

import com.ionos.player.cache.PlayerSourceInfoCache
import com.owncloud.android.datamodel.OCFile
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.transformation.PlayerTransformation
import javax.inject.Inject

class OCFileToPlayerFileInfoTransformation @Inject constructor(
    private val cache: PlayerSourceInfoCache,
) : PlayerTransformation<OCFile, PlayerFileInfo> {

    override fun transform(from: OCFile): PlayerFileInfo {
        return PlayerFileInfo(
            from.remotePath,
            from.fileLength,
            null
        )
            .also { cache[it] = from }
    }

}