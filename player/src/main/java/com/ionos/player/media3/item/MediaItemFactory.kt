package com.ionos.player.media3.item

import androidx.media3.common.MediaItem

interface MediaItemFactory<SourceInfo> {
	fun create(sourceInfo: SourceInfo): MediaItem
}
