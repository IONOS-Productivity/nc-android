package com.ionos.player.model.store

import com.ionos.player.model.PlayerFileInfo

interface SourceInfoStore {

	fun getSourceInfos(): List<PlayerFileInfo>

	fun setSourceInfos(sourceInfos: List<PlayerFileInfo>)

	fun getSourceInfo(mediaId: String): PlayerFileInfo?

	fun clear()
}
