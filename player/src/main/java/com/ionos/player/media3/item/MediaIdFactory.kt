package com.ionos.player.media3.item

interface MediaIdFactory<SourceInfo> {
	fun create(sourceInfo: SourceInfo): String
}
