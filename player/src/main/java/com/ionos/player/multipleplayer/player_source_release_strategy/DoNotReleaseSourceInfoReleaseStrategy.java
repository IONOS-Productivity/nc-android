/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayer.player_source_release_strategy;

import com.ionos.player.multipleplayer.interfaces.SourceInfoReleaseStrategy;

import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public class DoNotReleaseSourceInfoReleaseStrategy<SourceInfo> implements SourceInfoReleaseStrategy<SourceInfo> {
	@Override
	public boolean releaseCurrentPlayback(List<SourceInfo> newSourceInfos, SourceInfo currentSourceInfo) {
		return false;
	}
}
