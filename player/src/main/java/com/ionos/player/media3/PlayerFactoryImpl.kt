package com.ionos.player.media3

import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.ionos.player.media3.player.CreateExoPlayer
import com.ionos.player.media3.player.CreatePlayer
import com.ionos.player.player_mode.PlayerMode

@UnstableApi
class PlayerFactoryImpl(
	private val createExoPlayer: CreateExoPlayer,
	private val createChromeCastPlayer: CreatePlayer,
) : PlayerFactory {

	override fun create(mode: PlayerMode.Mode): Player {
		return when (mode) {
			PlayerMode.Mode.REGULAR -> createExoPlayer()
			PlayerMode.Mode.CHROMECAST -> createChromeCastPlayer()
		}
	}

}
