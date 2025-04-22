package com.strato.hidrive.player.player_source_release_strategy

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.viseven.develop.multipleplayer.interfaces.SourceInfoReleaseStrategy

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
