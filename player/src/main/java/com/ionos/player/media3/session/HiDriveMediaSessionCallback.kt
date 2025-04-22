package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import javax.inject.Inject

@UnstableApi
class HiDriveMediaSessionCallback @Inject constructor(
	private val sessionHolder: HiDriveMediaSessionHolder,
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
}
