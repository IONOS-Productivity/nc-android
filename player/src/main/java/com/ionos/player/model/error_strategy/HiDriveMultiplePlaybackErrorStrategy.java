/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.error_strategy;

import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.state.MultiplePlaybackState;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yaz on 1/23/17.
 */

public class HiDriveMultiplePlaybackErrorStrategy implements MultiplePlaybackErrorStrategy {

	@Inject
	public HiDriveMultiplePlaybackErrorStrategy(){
	}

	@Override
	public boolean switchToNextSource(Throwable throwable, final MultiplePlaybackState multiplePlaybackState) {
		final List<PlayerFileInfo> sourceInfos = multiplePlaybackState.currentSourceInfos;
		boolean oneFileInQueue = sourceInfos.size() == 1;
		boolean endOfQueue = multiplePlaybackState.getCurrentPlaybackState()
				.map(playbackState -> sourceInfos.indexOf(playbackState.sourceInfo) == sourceInfos.size() - 1)
				.orElse(false);
		return !oneFileInQueue && !endOfQueue;
	}
}
