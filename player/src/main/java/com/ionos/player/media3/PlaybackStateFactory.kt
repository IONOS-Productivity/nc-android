/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import androidx.media3.common.Player
import com.annimon.stream.Optional
import com.ionos.player.model.MultiplePlaybackSettings
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.state.MultiplePlaybackState
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.State
import com.ionos.player.model.state.VideoSize
import com.ionos.player.model.store.SourceInfoStore

class PlaybackStateFactory(
	private val sourceInfoStore: SourceInfoStore,
	private val playbackSettings: MultiplePlaybackSettings,
) {

	fun create(player: Player?): Optional<MultiplePlaybackState> {
		val state = MultiplePlaybackState(
			sourceInfoStore.getSourceInfos(),
			getCurrentPlaybackState(player),
			playbackSettings.isRepeatSingle,
			playbackSettings.isShuffle,
		)
		return Optional.of(state)
	}

	private fun getCurrentPlaybackState(player: Player?): Optional<PlaybackState> {
		val currentSourceInfo = player?.currentSourceInfo()
		return if (currentSourceInfo != null) {
			Optional.of(player.getCurrentPlaybackState(currentSourceInfo))
		} else {
			Optional.empty()
		}
	}

	private fun Player.getCurrentPlaybackState(currentSourceInfo: PlayerFileInfo) = PlaybackState(
		mapState(),
		currentPosition.toInt(),
		Optional.of(duration.toInt()),
		currentSourceInfo,
		mapVideoSize(),
	)

	private fun Player.currentSourceInfo(): PlayerFileInfo? {
		return currentMediaItem?.let { sourceInfoStore.getSourceInfo(it.mediaId) }
	}

	private fun Player.mapState(): State = when (playbackState) {
		Player.STATE_IDLE -> State.IDLE
		Player.STATE_ENDED -> State.COMPLETED
		Player.STATE_BUFFERING, Player.STATE_READY -> if (playWhenReady) State.PLAYING else State.PAUSED
		else -> State.NONE
	}

	private fun Player.mapVideoSize(): Optional<VideoSize> {
		return videoSize
			.takeIf { it.height > 0 && it.width > 0 }
			?.let { VideoSize(it.height, it.width) }
			.let { Optional.ofNullable(it) }
	}
}
