/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player;

import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.multipleplayermvp.interfaces.ControlAvailabilityStrategy;

import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public class HiDriveCyclicNextControlAvailabilityStrategy implements ControlAvailabilityStrategy<PlayerFileInfo> {
	@Override
	public boolean available(List<PlayerFileInfo> sourceInfos, PlayerFileInfo currentSourceInfo, boolean shuffle) {
		return !(sourceInfos.isEmpty() || sourceInfos.size() == 1);
	}
}
