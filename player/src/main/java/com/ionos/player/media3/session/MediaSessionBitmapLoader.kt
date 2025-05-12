package com.ionos.player.media3.session

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.BitmapLoader
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSourceBitmapLoader
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import com.ionos.player.R
import com.ionos.player.media3.common.mediaId
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.ThumbnailLoader
import com.ionos.player.model.file_store.PlaybackFileStore
import com.ionos.player.model.predicate.IsVideoPredicate
import com.ionos.player.util.SystemVersion
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@UnstableApi
class MediaSessionBitmapLoader(
	private val context: Context,
	private val playbackFileStore: PlaybackFileStore,
	private val thumbnailLoader: ThumbnailLoader,
	private val isVideoPredicate: IsVideoPredicate,
	private val delegate: BitmapLoader = DataSourceBitmapLoader(context),
) : BitmapLoader by delegate {

	companion object {
		private const val THUMBNAIL_TARGET_SIZE = 160
		private const val LARGE_THUMBNAIL_TARGET_SIZE = 320
	}

	private val executorService: ListeningExecutorService by lazy {
		MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor())
	}

	private var previousRequest: BitmapRequest? = null

	override fun loadBitmapFromMetadata(metadata: MediaMetadata): ListenableFuture<Bitmap>? {
		val mediaId = metadata.mediaId
		val previousRequest = this.previousRequest

		if (previousRequest != null &&
			previousRequest.mediaId == mediaId &&
			previousRequest.artworkData.contentEquals(metadata.artworkData) &&
			previousRequest.artworkUri == metadata.artworkUri
		) {
			return previousRequest.bitmapFuture
		}

		val bitmapFuture = executorService.submit(Callable {
			getBitmapFromMetadata(metadata) ?: run {
				val file = mediaId?.let(playbackFileStore::getFile)
				file?.let(::getBitmapForFile) ?: getDefaultBitmap(file)
			}
		})

		this.previousRequest = BitmapRequest(
			mediaId,
			metadata.artworkData,
			metadata.artworkUri,
			bitmapFuture,
		)

		return bitmapFuture
	}

	private fun getBitmapFromMetadata(metadata: MediaMetadata): Bitmap? {
		return try {
			delegate.loadBitmapFromMetadata(metadata)?.get()
		} catch (e: Exception) {
			null
		}
	}

	private fun getBitmapForFile(file: PlaybackFile): Bitmap? {
		val request = if (SystemVersion.greaterOrEqualToTiramisu()) {
            thumbnailLoader.load(context, file, LARGE_THUMBNAIL_TARGET_SIZE, LARGE_THUMBNAIL_TARGET_SIZE)
        } else {
            thumbnailLoader.load(context, file, THUMBNAIL_TARGET_SIZE, THUMBNAIL_TARGET_SIZE)
        }

		return try {
			request.get()
		} catch (e: Exception) {
			null
		}
	}

	private fun getDefaultBitmap(file: PlaybackFile?): Bitmap {
		val drawable = if (file != null && isVideoPredicate.satisfied(file)) {
			ContextCompat.getDrawable(context, R.drawable.ic_player_notification_video)
		} else {
			ContextCompat.getDrawable(context, R.drawable.ic_player_notification_audio)
		}
		return drawable?.toBitmap() ?: throw IllegalStateException("Could not decode resource")
	}

	private class BitmapRequest(
		val mediaId: String?,
		val artworkData: ByteArray?,
		val artworkUri: Uri?,
		val bitmapFuture: ListenableFuture<Bitmap>,
	)
}
