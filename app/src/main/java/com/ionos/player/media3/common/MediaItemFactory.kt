package com.ionos.player.media3.common

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.ionos.player.model.PlaybackFile
import javax.inject.Inject

class MediaItemFactory @Inject constructor() {

	fun create(file: PlaybackFile): MediaItem {
		return MediaItem
			.Builder()
			.setMediaId(file.id)
			.setUri(file.uri)
			.setMediaMetadata(createMetadata(file))
			.setMimeType(file.mimeType)
			.build()
	}

	private fun createMetadata(file: PlaybackFile): MediaMetadata {
		return MediaMetadata
			.Builder()
			.setExtras(file)
			.build()
	}
}
