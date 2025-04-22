/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayer.multiple_playback_error_strategy;

import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackErrorStrategy;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;

/**
 * Created by yaz on 1/23/17.
 */

public class SwitchToNextSourceMultiplePlaybackErrorStrategy<SourceInfo, Mode> implements MultiplePlaybackErrorStrategy<SourceInfo, Mode> {
	@Override
	public boolean switchToNextSource(Throwable error, MultiplePlaybackState<SourceInfo, Mode> state) {
		return true;
	}
}
