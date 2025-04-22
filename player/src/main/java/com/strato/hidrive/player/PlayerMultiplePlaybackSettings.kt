package com.strato.hidrive.player

import com.strato.hidrive.player.player_mode.PlayerMode
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlaybackSettings

abstract class PlayerMultiplePlaybackSettings(
	private val shouldRepeatAll: Boolean
): MultiplePlaybackSettings<PlayerMode.Mode> {

	private var _mode = PlayerMode.Mode.REGULAR


	override fun getRepeatMode(): MultiplePlaybackSettings.RepeatMode {
		return if (isRepeatSingle) {
			MultiplePlaybackSettings.RepeatMode.SINGLE
		} else if (this.shouldRepeatAll) {
			MultiplePlaybackSettings.RepeatMode.ALL
		} else {
			MultiplePlaybackSettings.RepeatMode.OFF
		}
	}

	override fun getMode(): PlayerMode.Mode {
		return _mode
	}

	override fun setMode(mode: PlayerMode.Mode) {
		_mode = mode
	}

}