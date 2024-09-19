package com.strato.hidrive.player.media3.item

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.media3.setMediaId
import com.strato.hidrive.player.transformation.FileInfoToDisplayNameTransformation
import com.strato.hidrive.player.transformation.FileInfoToMimetypeTransformation
import com.strato.hidrive.player.transformation.FileInfoToUriTransformation
import com.viseven.develop.media3.item.MediaIdFactory
import com.viseven.develop.media3.item.MediaItemFactory
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
