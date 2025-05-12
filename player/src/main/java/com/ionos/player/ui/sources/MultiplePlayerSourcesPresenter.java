/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources;

import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.ui.MultiplePlayer;
import com.ionos.player.ui.message.ExceptionToMessageTransformation;
import com.ionos.player.ui.message.PlayerExceptionMessageProvider;
import com.ionos.player.ui.sources.destroy_strategy.MultiplePlayerPresenterDestroyStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerSourcesPresenter implements MultiplePlayer.SourcesPresenter {

	private final PlaybackModel model;
	private final ExceptionToMessageTransformation exceptionToMessageTransformation;
    private MultiplePlayer.SourcesView view = NullMultiplePlayerSourcesView.getInstance();

	private final MultiplePlayerPresenterDestroyStrategy destroyStrategy;

	public MultiplePlayerSourcesPresenter(
			PlaybackModel model,
            MultiplePlayerPresenterDestroyStrategy destroyStrategy,
			PlayerExceptionMessageProvider exceptionMessageProvider) {
		this.model = model;
		this.destroyStrategy = destroyStrategy;
		this.exceptionToMessageTransformation = new ExceptionToMessageTransformation(exceptionMessageProvider);
	}

	@Override
	public void setView(MultiplePlayer.SourcesView view) {
		this.view = view != null ? view : NullMultiplePlayerSourcesView.getInstance();
	}

	@Override
	public void onCreate() {
		updateView();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerSourcesView.getInstance();
		this.destroyStrategy.onDestroy(this.model);
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

	@Override
	public void onSwitchToSourceInfo(PlayerFileInfo sourceInfo) {
		this.model.switchToSourceInfo(sourceInfo);
	}

	private void updateView() {
		updateView(this.model.getState());
	}

	private void updateView(Optional<PlaybackState> state) {
		List<PlayerFileInfo> sources = new ArrayList<>();

		if (state.isPresent()) {
			sources = state.get().currentSourceInfos;
		}

		this.view.displaySourceInfos(sources);
		if (state.map(input -> input.currentPlaybackItemState.isPresent()).orElse(false)) {
			PlaybackItemState playbackItemState = state.get().currentPlaybackItemState.get();
			this.view.displayCurrentSourceInfo(playbackItemState.sourceInfo);
		}
	}

	private final PlaybackModel.Listener listener = new PlaybackModel.Listener() {
		@Override
		public void onUpdate(PlaybackState state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<PlayerFileInfo> originalSourceInfos, List<PlayerFileInfo> currentSourceInfos) {
			view.displaySourceInfos(currentSourceInfos);
		}
	};
}
