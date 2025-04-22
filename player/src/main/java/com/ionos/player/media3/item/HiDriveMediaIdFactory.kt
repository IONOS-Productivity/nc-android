package com.ionos.player.media3.item

import com.ionos.player.domain.PlayerFileInfo
import javax.inject.Inject

class HiDriveMediaIdFactory @Inject constructor() : MediaIdFactory<PlayerFileInfo> {

	override fun create(sourceInfo: PlayerFileInfo): String {
		return sourceInfo.id
	}
}
