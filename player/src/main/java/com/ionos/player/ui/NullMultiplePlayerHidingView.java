/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerHidingView implements MultiplePlayer.HidingView {

	private static final NullMultiplePlayerHidingView INSTANCE = new NullMultiplePlayerHidingView();

	public static NullMultiplePlayerHidingView getInstance() {
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
