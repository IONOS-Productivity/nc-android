/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.presenter;

import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.null_object.NullMultiplePlayerHidingView;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerHidingPresenter<SourceInfo> implements MultiplePlayer.HidingPresenter<SourceInfo> {

	private final MultiplePlayer.Model<SourceInfo> model;
	private MultiplePlayer.HidingView<SourceInfo> view = NullMultiplePlayerHidingView.getInstance();

	public MultiplePlayerHidingPresenter(MultiplePlayer.Model<SourceInfo> model) {
		this.model = model;
	}

	@Override
	public void setView(MultiplePlayer.HidingView<SourceInfo> view) {
		this.view = view != null ? view : NullMultiplePlayerHidingView.<SourceInfo>getInstance();
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

	private final MultiplePlayer.Model.Listener<SourceInfo> listener = new MultiplePlayer.Model.Listener<>() {
		@Override
		public void onUpdate(MultiplePlaybackState<SourceInfo> state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<SourceInfo> originalSourceInfos, List<SourceInfo> currentSourceInfos) {
			updateView();
		}
	};
}
