package com.ionos.player.transformation.player_file_info

import com.ionos.player.cache.PlayerSourceInfoCache
import com.owncloud.android.datamodel.OCFile
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.PlayerTransformation
import javax.inject.Inject

class PlayerFileInfoToOCFileTransformation @Inject constructor(
    private val cache: PlayerSourceInfoCache,
): PlayerTransformation<PlayerFileInfo, OCFile?>{

    override fun transform(from: PlayerFileInfo): OCFile? {
        return cache[from]
    }

}