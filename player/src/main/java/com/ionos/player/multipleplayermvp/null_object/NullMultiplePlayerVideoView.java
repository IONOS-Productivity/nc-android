/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.null_object;

import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.player.interfaces.VideoViewSetter;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerVideoView<SourceInfo> implements MultiplePlayer.VideoView<SourceInfo> {

	private static final NullMultiplePlayerVideoView INSTANCE = new NullMultiplePlayerVideoView();

	public static <SourceInfo> NullMultiplePlayerVideoView<SourceInfo> getInstance() {
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
	public void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo) {

	}

	@Override
	public void clearVideoView(VideoViewSetter setter) {

	}
}
