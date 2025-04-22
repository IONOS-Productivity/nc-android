package com.ionos.player.media3.session

import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.ionos.player.media3.PlayerFactory
import com.ionos.player.player_mode.PlayerMode
import javax.inject.Inject

@UnstableApi
class SwitchToModeCommandHandler @Inject constructor(
	private val playerFactory: PlayerFactory,
) {

	fun handle(session: MediaSession, mode: PlayerMode.Mode) {
		val newPlayer = playerFactory.create(mode)
		val previousPlayer = session.player
		val repeatMode = previousPlayer.repeatMode
		val shuffleModeEnabled = previousPlayer.shuffleModeEnabled

		newPlayer.addListener(object : Player.Listener {
			override fun onPlaybackStateChanged(playbackState: Int) {
				if (playbackState == Player.STATE_READY) {
					newPlayer.removeListener(this)
					newPlayer.repeatMode = repeatMode
					newPlayer.shuffleModeEnabled = shuffleModeEnabled
				}
			}
		})

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
