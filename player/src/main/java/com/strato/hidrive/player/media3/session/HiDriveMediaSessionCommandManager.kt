package com.strato.hidrive.player.media3.session

import android.os.Bundle
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.strato.hidrive.player.player_mode.PlayerMode
import com.viseven.develop.media3.session.MediaSessionCommandManager

interface HiDriveMediaSessionCommandManager : MediaSessionCommandManager<PlayerMode.Mode> {
	val closeCommand: SessionCommand

	fun getCustomCommands(): List<SessionCommand>
	fun handleCustomCommand(session: MediaSession, customCommand: SessionCommand, args: Bundle)
}