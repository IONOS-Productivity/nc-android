package com.strato.hidrive.player.media3.item

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.strato.hidrive.player.chromecast.ChromecastNetworkUrlFactory
import com.strato.hidrive.player.chromecast.ChromecastTitleFactory
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.media3.setMediaId
import com.strato.hidrive.player.predicate.UseDefaultCastArtworkPredicate
import com.strato.hidrive.player.transformation.FileInfoToMimetypeTransformation
import com.viseven.develop.media3.item.MediaIdFactory
import javax.inject.Inject

@UnstableApi
class HiDriveCastMediaItemFactory @Inject constructor(
	private val mediaIdFactory: MediaIdFactory<PlayerFileInfo>,
	private val urlFactory: ChromecastNetworkUrlFactory,
	private val useDefaultCastArtworkPredicate: UseDefaultCastArtworkPredicate,
	private val chromecastTitleFactory: ChromecastTitleFactory,
	private val fileInfoToMimetypeTransformation: FileInfoToMimetypeTransformation,
) {

	companion object {
		private const val MAX_THUMBNAIL_SIZE = 350
	}

	fun create(sourceInfo: PlayerFileInfo): MediaItem {
		val mediaId = mediaIdFactory.create(sourceInfo)
		return MediaItem
			.Builder()
			.setMediaId(mediaId)
			.setUri(Uri.parse(urlFactory.createSourceUrl(sourceInfo)))
			.setMediaMetadata(createMediaMetadata(mediaId, sourceInfo))
			.setMimeType(fileInfoToMimetypeTransformation.transform(sourceInfo))
			.build()
	}

	private fun createMediaMetadata(mediaId: String, sourceInfo: PlayerFileInfo): MediaMetadata {
		return MediaMetadata
			.Builder()
			.setMediaId(mediaId)
			.setTitle(chromecastTitleFactory.getTitle(sourceInfo))
			.setSubtitle(chromecastTitleFactory.getSubtitle(sourceInfo))
			.setArtworkUri(createArtworkUri(sourceInfo))
			.build()
	}

	private fun createArtworkUri(sourceInfo: PlayerFileInfo): Uri? {
		return if (useDefaultCastArtworkPredicate.satisfied(sourceInfo)) {
			null // will use default receiver artwork
		} else {
			Uri.parse(urlFactory.createThumbnailUrl(sourceInfo, MAX_THUMBNAIL_SIZE))
		}
	}
}
