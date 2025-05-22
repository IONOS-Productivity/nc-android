package com.ionos.player.model.file_store

import com.ionos.player.model.PlaybackFile

interface PlaybackFileStore {

	fun getFiles(): List<PlaybackFile>

	fun setFiles(files: List<PlaybackFile>)

	fun getFile(id: String): PlaybackFile?

	fun clear()
}
