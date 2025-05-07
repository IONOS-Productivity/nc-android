/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources.destroy_strategy;

import com.ionos.player.model.MultiplePlayer;

/**
 * User: zuzik
 * Date: 7/4/16
 */
public interface MultiplePlayerPresenterDestroyStrategy<SourceInfo> {
	void onDestroy(MultiplePlayer.Model<SourceInfo> model);
}
