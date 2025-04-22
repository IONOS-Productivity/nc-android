package com.ionos.player.media3.item

import androidx.media3.cast.DefaultMediaItemConverter
import androidx.media3.cast.MediaItemConverter
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import com.google.android.gms.cast.MediaQueueItem
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.media3.store.SourceInfoStore
import javax.inject.Inject

@UnstableApi
class HiDriveMediaItemConverter private constructor(
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val mediaItemFactory: MediaItemFactory<PlayerFileInfo>,
	private val castMediaItemFactory: HiDriveCastMediaItemFactory,
	private val delegate: MediaItemConverter,
) : MediaItemConverter by delegate {

	@Inject
	constructor(
		sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
		mediaItemFactory: MediaItemFactory<PlayerFileInfo>,
		castMediaItemFactory: HiDriveCastMediaItemFactory,
	) : this(
		sourceInfoStore,
		mediaItemFactory,
		castMediaItemFactory,
		DefaultMediaItemConverter(),
	)

	override fun toMediaQueueItem(mediaItem: MediaItem): MediaQueueItem {
		val sourceInfo = sourceInfoStore.getSourceInfo(mediaItem.mediaId)
		return if (sourceInfo != null) {
			val castMediaItem = castMediaItemFactory.create(sourceInfo)
			delegate.toMediaQueueItem(castMediaItem)
		} else {
			delegate.toMediaQueueItem(mediaItem)
		}
	}

	override fun toMediaItem(mediaQueueItem: MediaQueueItem): MediaItem {
		val sourceInfo = mediaQueueItem.media?.contentId?.let(sourceInfoStore::getSourceInfo)
		return if (sourceInfo != null) {
			mediaItemFactory.create(sourceInfo)
		} else {
			delegate.toMediaItem(mediaQueueItem)
		}
	}
}
