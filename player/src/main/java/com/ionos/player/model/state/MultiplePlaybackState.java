/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state;

import com.annimon.stream.Optional;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlaybackState<SourceInfo> implements Serializable {
	public final List<SourceInfo> currentSourceInfos;
	@Nullable
	private final PlaybackState<SourceInfo> currentPlaybackState;
	public final boolean repeatSingle;
	public final boolean shuffle;

	public Optional<PlaybackState<SourceInfo>> getCurrentPlaybackState() {
		return Optional.ofNullable(this.currentPlaybackState);
	}

	public MultiplePlaybackState(
			List<SourceInfo> currentSourceInfos,
			Optional<PlaybackState<SourceInfo>> currentPlaybackState,
			boolean repeatSingle,
			boolean shuffle) {
		this.currentSourceInfos = currentSourceInfos;
		this.currentPlaybackState = currentPlaybackState.orElse(null);
		this.repeatSingle = repeatSingle;
		this.shuffle = shuffle;
	}
}
