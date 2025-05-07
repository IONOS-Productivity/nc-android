package com.ionos.player.media3.common

interface MediaIdFactory<SourceInfo> {
	fun create(sourceInfo: SourceInfo): String
}
