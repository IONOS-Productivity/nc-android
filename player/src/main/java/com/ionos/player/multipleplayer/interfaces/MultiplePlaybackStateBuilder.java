/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayer.interfaces;

import com.annimon.stream.Optional;
import com.ionos.player.player.interfaces.PlaybackState;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlaybackStateBuilder<SourceInfo, Mode> {

	private List<SourceInfo> originalSourceInfos;
	private List<SourceInfo> currentSourceInfos;
	private Optional<PlaybackState<SourceInfo>> currentPlaybackState;
	private boolean repeatSingle;
	private boolean shuffle;
	private Mode mode;

	MultiplePlaybackStateBuilder(MultiplePlaybackState<SourceInfo, Mode> multiplePlaybackState) {
		this.currentSourceInfos = new ArrayList<>(multiplePlaybackState.currentSourceInfos);
		this.originalSourceInfos = new ArrayList<>(multiplePlaybackState.originalSourceInfos);
		this.currentPlaybackState = multiplePlaybackState.getCurrentPlaybackState();
		this.repeatSingle = multiplePlaybackState.repeatSingle;
		this.shuffle = multiplePlaybackState.shuffle;
		this.mode = multiplePlaybackState.mode;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> originalSourceInfos(List<SourceInfo> sourceInfos) {
		this.originalSourceInfos = new ArrayList<>(sourceInfos);
		return this;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> currentSourceInfos(List<SourceInfo> sourceInfos) {
		this.currentSourceInfos = new ArrayList<>(sourceInfos);
		return this;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> currentPlaybackState(Optional<PlaybackState<SourceInfo>> currentPlaybackState) {
		this.currentPlaybackState = currentPlaybackState;
		return this;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> repeatSingle(boolean repeatSingle) {
		this.repeatSingle = repeatSingle;
		return this;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> shuffle(boolean shuffle) {
		this.shuffle = shuffle;
		return this;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> mode(Mode mode) {
		this.mode = mode;
		return this;
	}

	public MultiplePlaybackState<SourceInfo, Mode> build() {
		return new MultiplePlaybackState<>(
				new ArrayList<>(this.currentSourceInfos),
				new ArrayList<>(this.originalSourceInfos),
				this.currentPlaybackState,
				this.repeatSingle,
				this.shuffle,
				this.mode);
	}
}
