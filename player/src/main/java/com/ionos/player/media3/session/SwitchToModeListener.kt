package com.ionos.player.media3.session

import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.ionos.player.chromecast.PlayerChromecastModel
import com.ionos.player.chromecast.PlayerChromecastModelState
import com.ionos.player.media3.PlayerFactory
import com.ionos.player.player_mode.PlayerMode
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@UnstableApi
class SwitchToModeListener @AssistedInject constructor(
	@Assisted private val session: MediaSession,
	@Assisted private val initialMode: PlayerMode.Mode,
	private val playerFactory: PlayerFactory,
) : PlayerChromecastModel.Listener {
	private var currentMode: PlayerMode.Mode = initialMode

	override fun onUpdate(state: PlayerChromecastModelState) {
		val mode = state.getPlayerMode()
		if (currentMode != mode) {
			currentMode = mode
			switchTo(mode)
		}
	}

	override fun onApplicationConnectedToCastSession() {
	}

	private fun switchTo(mode: PlayerMode.Mode) {
		val newPlayer = playerFactory.create(mode)
		val previousPlayer = session.player

		newPlayer.setMediaItems(
			List(previousPlayer.mediaItemCount, previousPlayer::getMediaItemAt),
			previousPlayer.currentMediaItemIndex,
			previousPlayer.currentPosition,
		)

		newPlayer.prepare()
		newPlayer.playWhenReady = previousPlayer.playWhenReady

		session.player = newPlayer

		previousPlayer.release()
	}
}
