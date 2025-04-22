/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.analyzer;

import com.ionos.player.player.interfaces.PlaybackState;
import com.ionos.player.player.interfaces.State;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: zuzik
 * Date: 8/20/16
 */
public class PlaybackStateAnalyzer<SourceInfo> {

	private static final List<State> ALLOWED_PLAY_STATES = Arrays.asList(State.IDLE, State.PAUSED, State.COMPLETED);
	private static final List<State> ALLOWED_PAUSE_STATES = Collections.singletonList(State.PLAYING);
	private static final List<State> ALLOWED_STOP_STATES = Arrays.asList(State.PLAYING, State.PAUSED, State.COMPLETED);

	private final PlaybackState<SourceInfo> state;

	public PlaybackStateAnalyzer(PlaybackState<SourceInfo> state) {
		this.state = state;
	}

	public boolean playAvailable() {
		return ALLOWED_PLAY_STATES.contains(this.state.state);
	}

	public boolean pauseAvailable() {
		return ALLOWED_PAUSE_STATES.contains(this.state.state);
	}

	public boolean stopAvailable() {
		return ALLOWED_STOP_STATES.contains(this.state.state);
	}

	public boolean preparing() {
		return this.state.state == State.PREPARING;
	}
}
