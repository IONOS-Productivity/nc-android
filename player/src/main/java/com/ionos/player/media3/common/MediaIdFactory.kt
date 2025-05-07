package com.ionos.player.media3.common

import com.ionos.player.model.PlayerFileInfo

interface MediaIdFactory {
	fun create(sourceInfo: PlayerFileInfo): String
}
