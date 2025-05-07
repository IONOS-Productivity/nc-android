package com.ionos.player.transformation.oc_file

import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.cache.PlayerSourceInfoCache
import com.ionos.player.transformation.PlayerTransformation
import com.owncloud.android.datamodel.OCFile
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