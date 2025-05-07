/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.MultiplePlayer;

/**
 * Created by Anton Shevchuk on 18.01.2017.
 */

public class NullMultiplePlayerErrorView<SourceInfo> implements  MultiplePlayer.ErrorView<SourceInfo> {

	private static final NullMultiplePlayerErrorView INSTANCE = new NullMultiplePlayerErrorView();

	public static <SourceInfo> NullMultiplePlayerErrorView <SourceInfo>getInstance() {
			return INSTANCE;
	}

	private NullMultiplePlayerErrorView() {
	}


	@Override
	public void showError(String message) {

	}
}
