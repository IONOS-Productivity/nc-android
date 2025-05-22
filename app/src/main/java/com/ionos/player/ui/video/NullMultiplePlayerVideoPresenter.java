/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.video;

import com.ionos.player.ui.MultiplePlayer;

/**
 * Created by yaz on 9/23/16.
 */

public class NullMultiplePlayerVideoPresenter implements MultiplePlayer.VideoPresenter {

	private static final NullMultiplePlayerVideoPresenter INSTANCE = new NullMultiplePlayerVideoPresenter();

	public static NullMultiplePlayerVideoPresenter getInstance() {
		return INSTANCE;
	}

	@Override
	public void setView(MultiplePlayer.VideoView view) {
	}

	@Override
	public void onVideoViewCreated() {
	}

	@Override
	public void onVideoViewDestroyed() {
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onAppear() {
	}

	@Override
	public void onDisappear() {
	}
}
