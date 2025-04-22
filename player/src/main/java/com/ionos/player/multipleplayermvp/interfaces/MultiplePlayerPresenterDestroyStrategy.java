/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.interfaces;

/**
 * User: zuzik
 * Date: 7/4/16
 */
public interface MultiplePlayerPresenterDestroyStrategy<SourceInfo, Mode> {
	void onDestroy(MultiplePlayer.Model<SourceInfo, Mode> model);
}
