/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.ui.MultiplePlayer;

import java.util.List;

public class NullMultiplePlayerSourcesView implements MultiplePlayer.SourcesView {

	private static final NullMultiplePlayerSourcesView INSTANCE = new NullMultiplePlayerSourcesView();

	public static NullMultiplePlayerSourcesView getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerSourcesView() {
	}


	@Override
	public void displayCurrentFile(PlaybackFile file) {

	}

	@Override
	public void displayFiles(List<PlaybackFile> files) {

	}
}
