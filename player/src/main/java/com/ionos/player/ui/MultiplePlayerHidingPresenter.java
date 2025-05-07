/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.state.MultiplePlaybackState;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerHidingPresenter implements MultiplePlayer.HidingPresenter {

	private final MultiplePlayer.Model model;
	private MultiplePlayer.HidingView view = NullMultiplePlayerHidingView.getInstance();

	public MultiplePlayerHidingPresenter(MultiplePlayer.Model model) {
		this.model = model;
	}

	@Override
	public void setView(MultiplePlayer.HidingView view) {
		this.view = view != null ? view : NullMultiplePlayerHidingView.getInstance();
	}

	@Override
	public void onCreate() {
		updateView();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerHidingView.getInstance();
	}

	@Override
	public void onAppear() {
		updateView();
		this.model.addListener(this.listener);
	}

	@Override
	public void onDisappear() {
		this.model.removeListener(this.listener);
	}

	private void updateView() {
		boolean hasSources = this.model.getState()
				.mapToBoolean(input -> !input.currentSourceInfos.isEmpty())
				.orElse(false);
		if (hasSources) {
			this.view.displayPlayerView();
		} else {
			this.view.doNotDisplayPlayerView();
		}
	}

	private final MultiplePlayer.Model.Listener listener = new MultiplePlayer.Model.Listener() {
		@Override
		public void onUpdate(MultiplePlaybackState state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<PlayerFileInfo> originalSourceInfos, List<PlayerFileInfo> currentSourceInfos) {
			updateView();
		}
	};
}
