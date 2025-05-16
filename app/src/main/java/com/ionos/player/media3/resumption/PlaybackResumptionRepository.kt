package com.ionos.player.media3.resumption

import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import com.ionos.player.media3.common.MediaItemFactory
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackFileType
import com.ionos.player.model.PlaybackFilesRepository
import com.ionos.player.model.getPlaybackUri
import com.owncloud.android.ui.fragment.SearchType
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PlaybackResumptionRepository @Inject constructor(
	private val playbackResumptionConfigStore: PlaybackResumptionConfigStore,
	private val playbackFilesRepository: PlaybackFilesRepository,
	private val mediaItemFactory: MediaItemFactory,
) {

	@UnstableApi
	suspend fun restorePlaylist(): PlaybackResumptionPlaylist {
		return try {
			val (currentFileId, folderId, fileType, searchType) = playbackResumptionConfigStore.loadConfig() ?: run {
				throw IllegalStateException("Playback resumption config is null")
			}
			val playbackFiles = playbackFilesRepository.load(folderId, fileType, searchType).ifEmpty {
				throw IllegalStateException("Playback files are empty")
			}
			playbackFiles.toPlaylist(currentFileId)
		} catch (t: Throwable) {
			if (t is CancellationException) throw t
			getStubPlaylist()
		}
	}

	fun saveConfig(currentFileId: String, folderId: Long, fileType: PlaybackFileType, searchType: SearchType?) {
		playbackResumptionConfigStore.saveConfig(currentFileId, folderId, fileType, searchType)
	}

	fun updateCurrentFileId(currentFileId: String) {
		playbackResumptionConfigStore.updateCurrentFileId(currentFileId)
	}

	fun clear() {
		playbackResumptionConfigStore.clear()
	}

	@UnstableApi
	private fun List<PlaybackFile>.toPlaylist(currentFileId: String) = PlaybackResumptionPlaylist(
		mediaItemsWithStartPosition = MediaItemsWithStartPosition(
			map { mediaItemFactory.create(it) },
			indexOfFirst { it.id == currentFileId },
			0,
		),
		playbackFiles = this,
	)

	/**
	 * Workaround to avoid internal media3 crash
	 */
	@UnstableApi
	private fun getStubPlaylist(): PlaybackResumptionPlaylist {
		val stubPlaybackFile = PlaybackFile(
			id = "0",
			uri = getPlaybackUri(0L).toString(),
			name = "",
			mimeType = "audio/mpeg",
			contentLength = 0L,
			lastModified = 0L,
		)
		return listOf(stubPlaybackFile).toPlaylist(stubPlaybackFile.id)
	}
}
