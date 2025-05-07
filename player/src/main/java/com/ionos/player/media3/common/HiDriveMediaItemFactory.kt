package com.ionos.player.media3.common

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.transformation.FileInfoToDisplayNameTransformation
import com.ionos.player.transformation.FileInfoToMimetypeTransformation
import com.ionos.player.transformation.FileInfoToUriTransformation
import javax.inject.Inject

class HiDriveMediaItemFactory @Inject constructor(
	private val mediaIdFactory: MediaIdFactory<PlayerFileInfo>,
	private val fileInfoToUriTransformation: FileInfoToUriTransformation,
	private val fileInfoToMimetypeTransformation: FileInfoToMimetypeTransformation,
	private val fileInfoToDisplayNameTransformation: FileInfoToDisplayNameTransformation,
) : MediaItemFactory<PlayerFileInfo> {

	override fun create(sourceInfo: PlayerFileInfo): MediaItem {
		val mediaId = mediaIdFactory.create(sourceInfo)
		return MediaItem
			.Builder()
			.setMediaId(mediaId)
			.setUri(fileInfoToUriTransformation.transform(sourceInfo))
			.setMediaMetadata(createMetadata(mediaId, sourceInfo))
			.setMimeType(fileInfoToMimetypeTransformation.transform(sourceInfo))
			.build()
	}

	private fun createMetadata(mediaId: String, sourceInfo: PlayerFileInfo): MediaMetadata {
		return MediaMetadata
			.Builder()
			.setMediaId(mediaId)
			// remove to allow ExoPlayer to extract the title from the tags
			.setTitle(fileInfoToDisplayNameTransformation.transform(sourceInfo))
			// remove to allow ExoPlayer to extract the artist from the tags
			.setArtist("")
			.build()
	}
}
