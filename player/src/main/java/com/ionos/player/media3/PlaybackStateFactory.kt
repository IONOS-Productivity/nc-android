/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import androidx.media3.common.Player
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.file_store.PlaybackFileStore
import com.ionos.player.model.state.PlaybackItemState
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.PlayerState
import com.ionos.player.model.state.RepeatMode
import com.ionos.player.model.state.VideoSize
import java.util.Optional

class PlaybackStateFactory(
	private val playbackFileStore: PlaybackFileStore,
) {

	fun create(player: Player?): Optional<PlaybackState> {
		val state = PlaybackState(
            playbackFileStore.getPlaybackFiles(),
			getCurrentPlaybackState(player),
			player.mapRepeatMode(),
			player?.shuffleModeEnabled == true,
		)
		return Optional.of(state)
	}

	private fun getCurrentPlaybackState(player: Player?): Optional<PlaybackItemState> {
		val currentSourceInfo = player?.currentSourceInfo()
		return if (currentSourceInfo != null) {
			Optional.of(player.getCurrentPlaybackState(currentSourceInfo))
		} else {
			Optional.empty()
		}
	}

	private fun Player.getCurrentPlaybackState(currentSourceInfo: PlayerFileInfo) = PlaybackItemState(
		currentSourceInfo,
        mapPlayerState(),
		mapVideoSize(),
		currentPosition.toInt(),
		duration.toInt(),
	)

	private fun Player.currentSourceInfo(): PlayerFileInfo? {
		return currentMediaItem?.let { playbackFileStore.getPlaybackFile(it.mediaId) }
	}

	private fun Player.mapPlayerState(): PlayerState = when (playbackState) {
		Player.STATE_IDLE -> PlayerState.IDLE
		Player.STATE_ENDED -> PlayerState.COMPLETED
		Player.STATE_BUFFERING, Player.STATE_READY -> if (playWhenReady) PlayerState.PLAYING else PlayerState.PAUSED
		else -> PlayerState.NONE
	}

	private fun Player.mapVideoSize(): Optional<VideoSize> {
		return videoSize
			.takeIf { it.width > 0 && it.height > 0 }
			?.let { VideoSize(width = it.width, height = it.height) }
			.let { Optional.ofNullable(it) }
	}

    private fun Player?.mapRepeatMode(): RepeatMode = when (this?.repeatMode) {
        Player.REPEAT_MODE_ONE -> RepeatMode.SINGLE
        Player.REPEAT_MODE_ALL -> RepeatMode.ALL
        else -> RepeatMode.OFF
    }
}
