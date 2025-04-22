package com.strato.hidrive.player.media3.item

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.viseven.develop.media3.item.MediaIdFactory
import javax.inject.Inject

class HiDriveMediaIdFactory @Inject constructor() : MediaIdFactory<PlayerFileInfo> {

	override fun create(sourceInfo: PlayerFileInfo): String {
		return sourceInfo.id
	}
}
