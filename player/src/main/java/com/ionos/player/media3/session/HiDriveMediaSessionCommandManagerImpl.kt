package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.ionos.player.player_mode.PlayerMode
import javax.inject.Inject

@UnstableApi
class HiDriveMediaSessionCommandManagerImpl @Inject constructor(
	private val switchToModeCommandHandler: SwitchToModeCommandHandler,
) : HiDriveMediaSessionCommandManager {

	companion object {
		private const val CLOSE_ACTION = "CLOSE_ACTION"
		private const val SWITCH_TO_MODE_ACTION = "SWITCH_TO_MODE_ACTION"
		private const val MODE_ARG = "MODE_ARG"
	}

	override val closeCommand = SessionCommand(CLOSE_ACTION, Bundle.EMPTY)
	override val switchToModeCommand = SessionCommand(SWITCH_TO_MODE_ACTION, Bundle.EMPTY)

	override fun getCustomCommands(): List<SessionCommand> {
		return listOf(closeCommand, switchToModeCommand)
	}

	override fun createSwitchToModeArgs(mode: PlayerMode.Mode): Bundle {
		return Bundle().apply { putSerializable(MODE_ARG, mode) }
	}

	override fun handleCustomCommand(session: MediaSession, customCommand: SessionCommand, args: Bundle) {
		if (customCommand.customAction == CLOSE_ACTION) {
			session.release()
		} else if (customCommand.customAction == SWITCH_TO_MODE_ACTION) {
			val mode = args.getSerializable(MODE_ARG) as? PlayerMode.Mode
			mode?.let { switchToModeCommandHandler.handle(session, it) }
		}
	}
}
