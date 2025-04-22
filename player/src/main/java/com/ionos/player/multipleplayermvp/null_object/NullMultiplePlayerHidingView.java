/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.null_object;

import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerHidingView<SourceInfo> implements MultiplePlayer.HidingView<SourceInfo> {

	private static final NullMultiplePlayerHidingView INSTANCE = new NullMultiplePlayerHidingView();

	public static <SourceInfo> NullMultiplePlayerHidingView<SourceInfo> getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerHidingView() {
	}

	@Override
	public void displayPlayerView() {

	}

	@Override
	public void doNotDisplayPlayerView() {

	}
}
