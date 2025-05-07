package com.ionos.player.transformation.oc_file

import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.cache.PlayerSourceInfoCache
import com.ionos.player.transformation.PlayerTransformation
import com.owncloud.android.datamodel.OCFile
import javax.inject.Inject

class PlayerFileInfoToOCFileTransformation @Inject constructor(
    private val cache: PlayerSourceInfoCache,
): PlayerTransformation<PlayerFileInfo, OCFile?>{

    override fun transform(from: PlayerFileInfo): OCFile? {
        return cache[from]
    }

}