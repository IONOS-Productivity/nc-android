package com.ionos.player.model.strategy.release

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class DefaultPlaybackReleaseStrategy @Inject constructor() : PlaybackReleaseStrategy {

	override fun releaseCurrentPlayback(sourceInfos: List<PlayerFileInfo>, currentSourceInfo: PlayerFileInfo): Boolean {
		return !sourceInfos.contains(currentSourceInfo)
	}
}
