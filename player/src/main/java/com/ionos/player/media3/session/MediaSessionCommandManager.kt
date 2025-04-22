package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.session.SessionCommand

interface MediaSessionCommandManager<Mode> {
	val switchToModeCommand: SessionCommand

	fun createSwitchToModeArgs(mode: Mode): Bundle
}
