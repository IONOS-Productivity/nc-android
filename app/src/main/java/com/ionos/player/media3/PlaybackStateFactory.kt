/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import androidx.media3.common.Player
import com.ionos.player.model.PlaybackFile
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
            playbackFileStore.getFiles(),
			getCurrentItemState(player),
			player.mapRepeatMode(),
			player?.shuffleModeEnabled == true,
		)
		return Optional.of(state)
	}

	private fun getCurrentItemState(player: Player?): Optional<PlaybackItemState> {
		val currentFile = player?.currentFile()
		return if (currentFile != null) {
			Optional.of(player.getCurrentItemState(currentFile))
		} else {
			Optional.empty()
		}
	}

	private fun Player.getCurrentItemState(currentFile: PlaybackFile) = PlaybackItemState(
		currentFile,
        mapPlayerState(),
		mapVideoSize(),
		currentPosition.toInt(),
		duration.toInt(),
	)

	private fun Player.currentFile(): PlaybackFile? {
		return currentMediaItem?.let { playbackFileStore.getFile(it.mediaId) }
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
