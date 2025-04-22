/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.presenter_destroy_strategy;

import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayerPresenterDestroyStrategy;

/**
 * User: zuzik
 * Date: 7/4/16
 */
public class DoNothingMultiplePlayerPresenterDestroyStrategy<SourceInfo> implements MultiplePlayerPresenterDestroyStrategy<SourceInfo> {
	@Override
	public void onDestroy(MultiplePlayer.Model<SourceInfo> model) {

	}
}
