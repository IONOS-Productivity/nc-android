/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources.destroy_strategy;

import com.ionos.player.model.PlaybackModel;

public interface MultiplePlayerPresenterDestroyStrategy {
	void onDestroy(PlaybackModel model);
}
