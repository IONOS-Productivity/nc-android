package com.ionos.player.model.strategy.release

import com.ionos.player.model.PlayerFileInfo
import java.io.Serializable

interface PlaybackReleaseStrategy : Serializable {
    fun releaseCurrentPlayback(sourceInfos: List<PlayerFileInfo>, currentSourceInfo: PlayerFileInfo): Boolean
}
