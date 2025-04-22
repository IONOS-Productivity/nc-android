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
public class InMemoryMultiplePlaybackSettings implements MultiplePlaybackSettings {

	private final boolean shouldRepeatAll;
	private boolean repeatSingle;
	private boolean shuffle;

	public InMemoryMultiplePlaybackSettings(boolean shouldRepeatAll) {
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
}
