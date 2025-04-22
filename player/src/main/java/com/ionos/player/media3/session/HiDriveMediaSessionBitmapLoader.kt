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
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.image_loading.PlayerImageLoader
import com.ionos.player.image_loading.PlayerImageLoaderOptions
import com.ionos.player.media3.mediaId
import com.ionos.player.predicate.IsVideoPredicate
import com.ionos.player.util.SystemVersion
import com.ionos.player.R
import com.ionos.player.media3.store.SourceInfoStore
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@UnstableApi
class HiDriveMediaSessionBitmapLoader(
	private val context: Context,
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val imageLoader: PlayerImageLoader,
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
				val sourceInfo = mediaId?.let(sourceInfoStore::getSourceInfo)
				sourceInfo?.let(::getBitmapForSourceInfo) ?: getDefaultBitmap(sourceInfo)
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

	private fun getBitmapForSourceInfo(sourceInfo: PlayerFileInfo): Bitmap? {
		val request = imageLoader.load(sourceInfo)?.run {
			if (SystemVersion.greaterOrEqualToTiramisu()) {
				submit(context, LARGE_THUMBNAIL_TARGET_SIZE, LARGE_THUMBNAIL_TARGET_SIZE)
			} else {
				options(PlayerImageLoaderOptions(PlayerImageLoaderOptions.ScaleType.CENTER_CROP))
				submit(context, THUMBNAIL_TARGET_SIZE, THUMBNAIL_TARGET_SIZE)
			}
		}

		return try {
			request?.get()
		} catch (e: Exception) {
			null
		}
	}

	private fun getDefaultBitmap(sourceInfo: PlayerFileInfo?): Bitmap {
		val drawable = if (sourceInfo != null && isVideoPredicate.satisfied(sourceInfo)) {
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
