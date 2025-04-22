/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.interfaces;

import com.annimon.stream.Optional;

import java.io.Serializable;

import androidx.annotation.Nullable;

/**
 * User: zuzik
 * Date: 6/5/16
 */
public class PlaybackState<SourceInfo> implements Serializable {

	public final State state;
	public final int currentTimeInMilliseconds;
	@Nullable
	private final Integer maxTimeInMilliseconds;
	public final SourceInfo sourceInfo;
	public final Optional<VideoSize> videoSize;

	public Optional<Integer> getMaxTimeInMilliseconds() {
		return Optional.ofNullable(this.maxTimeInMilliseconds);
	}

	public PlaybackState(
			State state,
			int currentTimeInMilliseconds,
			Optional<Integer> maxTimeInMilliseconds,
			SourceInfo sourceInfo,
			Optional<VideoSize> videoSize) {
		this.state = state;
		this.currentTimeInMilliseconds = currentTimeInMilliseconds;
		this.maxTimeInMilliseconds = maxTimeInMilliseconds.orElse(null);
		this.sourceInfo = sourceInfo;
		this.videoSize = videoSize;
	}
}
