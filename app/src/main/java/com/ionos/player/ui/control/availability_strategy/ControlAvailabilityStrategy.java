/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control.availability_strategy;

import com.ionos.player.model.PlaybackFile;

import java.util.List;

public interface ControlAvailabilityStrategy {
	boolean available(List<PlaybackFile> files, PlaybackFile file, boolean shuffle);
}
