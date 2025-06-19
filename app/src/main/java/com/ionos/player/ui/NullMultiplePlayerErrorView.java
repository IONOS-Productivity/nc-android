/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

public class NullMultiplePlayerErrorView implements  MultiplePlayer.ErrorView {

	private static final NullMultiplePlayerErrorView INSTANCE = new NullMultiplePlayerErrorView();

	public static NullMultiplePlayerErrorView getInstance() {
			return INSTANCE;
	}

	private NullMultiplePlayerErrorView() {
	}


	@Override
	public void showError(int messageId) {
		// No operation, this is a null object pattern implementation

	}
}
