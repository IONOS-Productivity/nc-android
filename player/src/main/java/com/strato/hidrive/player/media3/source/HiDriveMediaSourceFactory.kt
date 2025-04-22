package com.strato.hidrive.player.media3.source

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.extractor.DefaultExtractorsFactory
import com.strato.hidrive.player.transformation.MediaItemToDataSourceFactoryTransformation

@UnstableApi
class HiDriveMediaSourceFactory(
	private val mediaItemToDataSourceFactoryTransformation: MediaItemToDataSourceFactoryTransformation,
) : MediaSource.Factory {

	override fun setDrmSessionManagerProvider(drmSessionManagerProvider: DrmSessionManagerProvider): MediaSource.Factory {
		return this
	}

	override fun setLoadErrorHandlingPolicy(loadErrorHandlingPolicy: LoadErrorHandlingPolicy): MediaSource.Factory {
		return this
	}

	override fun getSupportedTypes(): IntArray {
		return intArrayOf(C.CONTENT_TYPE_OTHER)
	}

	override fun createMediaSource(mediaItem: MediaItem): MediaSource {
		val dataSourceFactory = mediaItemToDataSourceFactoryTransformation.transform(mediaItem)
		return ProgressiveMediaSource
			.Factory(dataSourceFactory, DefaultExtractorsFactory())
			.createMediaSource(mediaItem)
	}

}
