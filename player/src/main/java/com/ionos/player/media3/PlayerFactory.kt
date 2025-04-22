package com.ionos.player.media3

import androidx.media3.common.Player
import com.ionos.player.player_mode.PlayerMode

interface PlayerFactory {
	fun create(mode: PlayerMode.Mode): Player
}