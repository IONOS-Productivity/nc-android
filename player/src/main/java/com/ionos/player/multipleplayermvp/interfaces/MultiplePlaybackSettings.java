/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.interfaces;

/**
 * User: zuzik
 * Date: 7/7/16
 */
public interface MultiplePlaybackSettings {

	RepeatMode getRepeatMode();

	boolean isRepeatSingle();

	void repeatSingle();

	void doNotRepeatSingle();

	boolean isShuffle();

	void shuffle();

	void doNotShuffle();

	enum RepeatMode {
		SINGLE,
		ALL,
		OFF,
	}
}
