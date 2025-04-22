/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player

import com.ionos.player.player_mode.PlayerMode
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlaybackSettings

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