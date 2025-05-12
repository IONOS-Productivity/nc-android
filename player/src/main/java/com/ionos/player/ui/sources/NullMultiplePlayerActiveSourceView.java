/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources;

import com.ionos.player.ui.MultiplePlayer;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerActiveSourceView implements MultiplePlayer.ActiveSourceView {

	private static final NullMultiplePlayerActiveSourceView INSTANCE = new NullMultiplePlayerActiveSourceView();

	public static NullMultiplePlayerActiveSourceView getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerActiveSourceView() {
	}

	@Override
	public void displayAsActiveSource() {

	}

	@Override
	public void displayAsInactiveSource() {

	}

	@Override
	public void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds) {

	}
}
