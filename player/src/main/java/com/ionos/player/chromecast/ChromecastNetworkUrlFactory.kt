package com.ionos.player.chromecast

import com.ionos.player.domain.PlayerFileInfo

interface ChromecastNetworkUrlFactory {
	fun createSourceUrl(sourceInfo: PlayerFileInfo): String
	fun createThumbnailUrl(sourceInfo: PlayerFileInfo, maxSize: Int): String
}