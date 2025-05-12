package com.ionos.player.media3.common

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class MediaItemFactory @Inject constructor() {

	fun create(sourceInfo: PlayerFileInfo): MediaItem {
		val mediaId = sourceInfo.id
		return MediaItem
			.Builder()
			.setMediaId(mediaId)
			.setUri(sourceInfo.uri)
			.setMediaMetadata(createMetadata(mediaId, sourceInfo))
			.setMimeType(sourceInfo.mimeType)
			.build()
	}

	private fun createMetadata(mediaId: String, sourceInfo: PlayerFileInfo): MediaMetadata {
		return MediaMetadata
			.Builder()
			.setMediaId(mediaId)
			// remove to allow ExoPlayer to extract the title from the tags
			.setTitle(sourceInfo.name)
			// remove to allow ExoPlayer to extract the artist from the tags
			.setArtist("")
			.build()
	}
}
