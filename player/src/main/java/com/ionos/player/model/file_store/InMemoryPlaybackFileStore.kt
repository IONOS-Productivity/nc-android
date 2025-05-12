package com.ionos.player.model.file_store

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryPlaybackFileStore @Inject constructor() : PlaybackFileStore {
	private val sourceInfos = mutableListOf<PlayerFileInfo>()

	override fun getPlaybackFiles(): List<PlayerFileInfo> {
		return synchronized(this) {
			sourceInfos.toList()
		}
	}

	override fun setPlaybackFiles(files: List<PlayerFileInfo>) {
		synchronized(this) {
			this.sourceInfos.clear()
			this.sourceInfos.addAll(files)
		}
	}

	override fun getPlaybackFile(id: String): PlayerFileInfo? {
		return synchronized(this) {
			sourceInfos.firstOrNull { id == it.id }
		}
	}

	override fun clear() {
		synchronized(this) {
			sourceInfos.clear()
		}
	}
}
