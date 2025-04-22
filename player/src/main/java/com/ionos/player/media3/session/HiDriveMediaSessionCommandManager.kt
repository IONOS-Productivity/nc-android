package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.ionos.player.player_mode.PlayerMode

interface HiDriveMediaSessionCommandManager : MediaSessionCommandManager<PlayerMode.Mode> {
	val closeCommand: SessionCommand

	fun getCustomCommands(): List<SessionCommand>
	fun handleCustomCommand(session: MediaSession, customCommand: SessionCommand, args: Bundle)
}