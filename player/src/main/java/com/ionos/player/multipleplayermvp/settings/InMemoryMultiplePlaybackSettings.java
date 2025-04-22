/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.settings;

import com.ionos.player.multipleplayermvp.interfaces.MultiplePlaybackSettings;

/**
 * User: zuzik
 * Date: 7/7/16
 */
public class InMemoryMultiplePlaybackSettings<Mode> implements MultiplePlaybackSettings<Mode> {

	private final boolean shouldRepeatAll;
	private boolean repeatSingle;
	private boolean shuffle;
	private Mode mode;

	public InMemoryMultiplePlaybackSettings(Mode mode, boolean shouldRepeatAll) {
		this.mode = mode;
		this.shouldRepeatAll = shouldRepeatAll;
	}

	@Override
	public RepeatMode getRepeatMode() {
		if (this.repeatSingle) {
			return RepeatMode.SINGLE;
		} else if (this.shouldRepeatAll) {
			return RepeatMode.ALL;
		} else {
			return RepeatMode.OFF;
		}
	}

	@Override
	public boolean isRepeatSingle() {
		return this.repeatSingle;
	}

	@Override
	public void repeatSingle() {
		this.repeatSingle = true;
	}

	@Override
	public void doNotRepeatSingle() {
		this.repeatSingle = false;
	}

	@Override
	public boolean isShuffle() {
		return this.shuffle;
	}

	@Override
	public void shuffle() {
		this.shuffle = true;
	}

	@Override
	public void doNotShuffle() {
		this.shuffle = false;
	}

	@Override
	public Mode getMode() {
		return this.mode;
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
	}
}
