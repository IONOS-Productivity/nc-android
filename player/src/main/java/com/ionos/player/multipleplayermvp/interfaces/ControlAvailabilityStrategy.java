/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.interfaces;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/18/16
 */
public interface ControlAvailabilityStrategy<SourceInfo> {
	boolean available(
			List<SourceInfo> sourceInfos,
			SourceInfo currentSourceInfo,
			boolean shuffle);
}
