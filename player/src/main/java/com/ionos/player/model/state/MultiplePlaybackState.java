/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state;

import com.annimon.stream.Optional;
import com.ionos.player.model.PlayerFileInfo;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlaybackState implements Serializable {
	public final List<PlayerFileInfo> currentSourceInfos;
	@Nullable
	private final PlaybackState currentPlaybackState;
	public final boolean repeatSingle;
	public final boolean shuffle;

	public Optional<PlaybackState> getCurrentPlaybackState() {
		return Optional.ofNullable(this.currentPlaybackState);
	}

	public MultiplePlaybackState(
			List<PlayerFileInfo> currentSourceInfos,
			Optional<PlaybackState> currentPlaybackState,
			boolean repeatSingle,
			boolean shuffle) {
		this.currentSourceInfos = currentSourceInfos;
		this.currentPlaybackState = currentPlaybackState.orElse(null);
		this.repeatSingle = repeatSingle;
		this.shuffle = shuffle;
	}
}
