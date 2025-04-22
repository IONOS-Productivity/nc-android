package com.ionos.player.media3.store

interface SourceInfoStore<SourceInfo> {

	fun getSourceInfos(): List<SourceInfo>

	fun setSourceInfos(sourceInfos: List<SourceInfo>)

	fun getSourceInfo(mediaId: String): SourceInfo?

	fun clear()
}
