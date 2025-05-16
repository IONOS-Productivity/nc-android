package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import com.ionos.player.media3.resumption.PlaybackResumptionRepository
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.file_store.PlaybackFileStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MediaSessionCallback @Inject constructor(
	private val sessionHolder: MediaSessionHolder,
	private val playbackResumptionRepository: PlaybackResumptionRepository,
	private val playbackFileStore: PlaybackFileStore,
	private val playbackModel: PlaybackModel,
) : MediaSession.Callback {

	companion object {
		const val CLOSE_ACTION = "CLOSE_ACTION"
	}

	override fun onConnect(
		session: MediaSession,
		controller: MediaSession.ControllerInfo
	): ConnectionResult {
		val connectionResult = super.onConnect(session, controller)
		val sessionCommandsBuilder = connectionResult.availableSessionCommands.buildUpon()
		sessionCommandsBuilder.add(SessionCommand(CLOSE_ACTION, Bundle.EMPTY))
		val sessionCommands = sessionCommandsBuilder.build()
		return ConnectionResult.accept(sessionCommands, connectionResult.availablePlayerCommands)
	}

	override fun onCustomCommand(
		session: MediaSession,
		controller: MediaSession.ControllerInfo,
		customCommand: SessionCommand,
		args: Bundle
	): ListenableFuture<SessionResult> {
		if (customCommand.customAction == CLOSE_ACTION) {
			sessionHolder.release()
		}
		return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
	}

	@UnstableApi
	override fun onPlaybackResumption(
		mediaSession: MediaSession,
		controller: MediaSession.ControllerInfo
	): ListenableFuture<MediaItemsWithStartPosition> {
		val future = SettableFuture.create<MediaItemsWithStartPosition>()
		GlobalScope.launch {
			try {
				val playlist = playbackResumptionRepository.restorePlaylist()
				playbackFileStore.setFiles(playlist.playbackFiles)
				playbackModel.start()
				future.set(playlist.mediaItemsWithStartPosition)
			} catch (t: Throwable) {
				future.setException(t)
			}
		}
		return future
	}
}
