/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.null_object;

import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;

import java.util.List;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerSourcesView<SourceInfo> implements MultiplePlayer.SourcesView<SourceInfo> {

	private static final NullMultiplePlayerSourcesView INSTANCE = new NullMultiplePlayerSourcesView();

	public static <SourceInfo> NullMultiplePlayerSourcesView<SourceInfo> getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerSourcesView() {
	}


	@Override
	public void displayCurrentSourceInfo(SourceInfo sourceInfo) {

	}

	@Override
	public void displaySourceInfos(List<SourceInfo> sourceInfos) {

	}
}
