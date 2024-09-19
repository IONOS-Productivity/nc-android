package com.strato.hidrive.player.chromecast

import com.strato.hidrive.player.domain.PlayerFileInfo

interface ChromecastNetworkUrlFactory {
	fun createSourceUrl(sourceInfo: PlayerFileInfo): String
	fun createThumbnailUrl(sourceInfo: PlayerFileInfo, maxSize: Int): String
}