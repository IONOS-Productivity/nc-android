package com.strato.hidrive.player.media3

import androidx.media3.common.Player
import com.strato.hidrive.player.player_mode.PlayerMode

interface PlayerFactory {
	fun create(mode: PlayerMode.Mode): Player
}