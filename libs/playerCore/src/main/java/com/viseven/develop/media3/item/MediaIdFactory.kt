package com.viseven.develop.media3.item

interface MediaIdFactory<SourceInfo> {
	fun create(sourceInfo: SourceInfo): String
}
