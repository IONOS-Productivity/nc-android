package com.ionos.player.model.file_store

import com.ionos.player.model.PlaybackFile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryPlaybackFileStore @Inject constructor() : PlaybackFileStore {
	private val files = mutableListOf<PlaybackFile>()

	override fun getFiles(): List<PlaybackFile> {
		return synchronized(this) {
			files.toList()
		}
	}

	override fun setFiles(files: List<PlaybackFile>) {
		synchronized(this) {
			this.files.clear()
			this.files.addAll(files)
		}
	}

	override fun getFile(id: String): PlaybackFile? {
		return synchronized(this) {
			files.firstOrNull { id == it.id }
		}
	}

	override fun clear() {
		synchronized(this) {
			files.clear()
		}
	}
}
