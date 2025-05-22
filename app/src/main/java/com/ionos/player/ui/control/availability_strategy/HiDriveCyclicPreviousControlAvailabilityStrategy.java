/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control.availability_strategy;

import com.ionos.player.model.PlaybackFile;

import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public class HiDriveCyclicPreviousControlAvailabilityStrategy implements ControlAvailabilityStrategy {
	@Override
	public boolean available(List<PlaybackFile> files, PlaybackFile file, boolean shuffle) {
		return !(files.isEmpty());
	}
}
