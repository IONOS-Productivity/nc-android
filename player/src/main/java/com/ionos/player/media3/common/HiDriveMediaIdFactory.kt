package com.ionos.player.media3.common

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class HiDriveMediaIdFactory @Inject constructor() : MediaIdFactory {

	override fun create(sourceInfo: PlayerFileInfo): String {
		return sourceInfo.id
	}
}
