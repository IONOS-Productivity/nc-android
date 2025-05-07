/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.video;

import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.VideoViewSetter;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerVideoView implements MultiplePlayer.VideoView {

	private static final NullMultiplePlayerVideoView INSTANCE = new NullMultiplePlayerVideoView();

	public static NullMultiplePlayerVideoView getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerVideoView() {
	}

	@Override
	public void setVideoViewAvailable() {

	}

	@Override
	public void setVideoViewUnavailable() {

	}

	@Override
	public void setVideoView(VideoViewSetter setter, PlayerFileInfo sourceInfo) {

	}

	@Override
	public void clearVideoView(VideoViewSetter setter) {

	}
}
