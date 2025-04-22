package com.ionos.player.chromecast

import com.ionos.player.domain.PlayerFileInfo

interface ChromecastTitleFactory {
	fun getTitle(fileInfo: PlayerFileInfo): String
	fun getSubtitle(fileInfo: PlayerFileInfo): String
}