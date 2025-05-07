package com.ionos.player.media3.common

import androidx.media3.common.MediaItem
import com.ionos.player.model.PlayerFileInfo

interface MediaItemFactory {
	fun create(sourceInfo: PlayerFileInfo): MediaItem
}
