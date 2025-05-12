package com.ionos.player.model.file_store

import com.ionos.player.model.PlayerFileInfo

interface PlaybackFileStore {

	fun getPlaybackFiles(): List<PlayerFileInfo>

	fun setPlaybackFiles(files: List<PlayerFileInfo>)

	fun getPlaybackFile(id: String): PlayerFileInfo?

	fun clear()
}
