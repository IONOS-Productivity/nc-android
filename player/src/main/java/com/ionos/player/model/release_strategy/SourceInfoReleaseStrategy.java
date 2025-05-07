/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.release_strategy;

import java.io.Serializable;
import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public interface SourceInfoReleaseStrategy<SourceInfo> extends Serializable {
	boolean releaseCurrentPlayback(
			List<SourceInfo> newSourceInfos,
			SourceInfo currentSourceInfo);
}
