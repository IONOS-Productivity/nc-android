package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

@UnstableApi
class HiDriveMediaSessionCallback(
	private val sessionCommandManager: HiDriveMediaSessionCommandManager,
) : MediaSession.Callback {

	override fun onConnect(
		session: MediaSession,
		controller: MediaSession.ControllerInfo
	): ConnectionResult {
		val connectionResult = super.onConnect(session, controller)
		val sessionCommandsBuilder = connectionResult.availableSessionCommands.buildUpon()
		sessionCommandManager.getCustomCommands().forEach(sessionCommandsBuilder::add)
		val sessionCommands = sessionCommandsBuilder.build()
		return ConnectionResult.accept(sessionCommands, connectionResult.availablePlayerCommands)
	}

	override fun onCustomCommand(
		session: MediaSession,
		controller: MediaSession.ControllerInfo,
		customCommand: SessionCommand,
		args: Bundle
	): ListenableFuture<SessionResult> {
		sessionCommandManager.handleCustomCommand(session, customCommand, args)
		return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
	}
}
