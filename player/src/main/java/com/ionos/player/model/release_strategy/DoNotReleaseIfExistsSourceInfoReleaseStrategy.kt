package com.ionos.player.model.release_strategy

import com.ionos.player.model.PlayerFileInfo

class DoNotReleaseIfExistsSourceInfoReleaseStrategy : SourceInfoReleaseStrategy<PlayerFileInfo> {

	override fun releaseCurrentPlayback(
		newSourceInfos: List<PlayerFileInfo>,
		currentSourceInfo: PlayerFileInfo
	): Boolean {
		return !newSourceInfos.any {
			it.id == currentSourceInfo.id && it.contentLength == currentSourceInfo.contentLength
		}
	}
}
