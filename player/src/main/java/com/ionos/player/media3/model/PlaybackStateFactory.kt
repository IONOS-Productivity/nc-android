/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.model

import androidx.media3.common.Player
import com.annimon.stream.Optional
import com.ionos.player.media3.store.SourceInfoStore
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlaybackSettings
import com.ionos.player.player.interfaces.PlaybackState
import com.ionos.player.player.interfaces.State
import com.ionos.player.player.interfaces.VideoSize

class PlaybackStateFactory<SourceInfo, Mode>(
	private val sourceInfoStore: SourceInfoStore<SourceInfo>,
	private val playbackSettings: MultiplePlaybackSettings<Mode>,
) {

	fun create(player: Player?): Optional<MultiplePlaybackState<SourceInfo, Mode>> {
		val state = MultiplePlaybackState(
			sourceInfoStore.getSourceInfos(),
			sourceInfoStore.getSourceInfos(),
			getCurrentPlaybackState(player),
			playbackSettings.isRepeatSingle,
			playbackSettings.isShuffle,
			playbackSettings.mode,
		)
		return Optional.of(state)
	}

	private fun getCurrentPlaybackState(player: Player?): Optional<PlaybackState<SourceInfo>> {
		val currentSourceInfo = player?.currentSourceInfo()
		return if (currentSourceInfo != null) {
			Optional.of(player.getCurrentPlaybackState(currentSourceInfo))
		} else {
			Optional.empty()
		}
	}

	private fun Player.getCurrentPlaybackState(currentSourceInfo: SourceInfo) = PlaybackState(
		mapState(),
		currentPosition.toInt(),
		Optional.of(duration.toInt()),
		playbackSettings.isRepeatSingle,
		currentSourceInfo,
		mapVideoSize(),
	)

	private fun Player.currentSourceInfo(): SourceInfo? {
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
