package com.strato.hidrive.player.chromecast

import com.strato.hidrive.player.domain.PlayerFileInfo

interface ChromecastTitleFactory {
	fun getTitle(fileInfo: PlayerFileInfo): String
	fun getSubtitle(fileInfo: PlayerFileInfo): String
}