/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources;

import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.PlayerFileInfo;

import java.util.List;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerSourcesView implements MultiplePlayer.SourcesView {

	private static final NullMultiplePlayerSourcesView INSTANCE = new NullMultiplePlayerSourcesView();

	public static NullMultiplePlayerSourcesView getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerSourcesView() {
	}


	@Override
	public void displayCurrentSourceInfo(PlayerFileInfo sourceInfo) {

	}

	@Override
	public void displaySourceInfos(List<PlayerFileInfo> sourceInfos) {

	}
}
