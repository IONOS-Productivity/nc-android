/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources;

import com.annimon.stream.Optional;
import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.State;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerActiveSourcePresenter implements MultiplePlayer.ActiveSourcePresenter {

	private final MultiplePlayer.Model model;
	private Optional<PlayerFileInfo> sourceInfo = Optional.empty();
	private MultiplePlayer.ActiveSourceView view = NullMultiplePlayerActiveSourceView.getInstance();

	public MultiplePlayerActiveSourcePresenter(MultiplePlayer.Model model) {
		this.model = model;
	}

	@Override
	public void setSourceInfo(PlayerFileInfo sourceInfo) {
		this.sourceInfo = Optional.of(sourceInfo);
		updateView();
	}

	@Override
	public void setView(MultiplePlayer.ActiveSourceView view) {
		this.view = view != null ? view : NullMultiplePlayerActiveSourceView.getInstance();
		updateView();
	}

	@Override
	public void onCreate() {
		updateView();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerActiveSourceView.getInstance();
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
		updateView(this.model.getState());
	}

	private void updateView(Optional<MultiplePlaybackState> state) {
		if (this.sourceInfo.isPresent() && state
				.mapToBoolean(input -> input.getCurrentPlaybackState().isPresent())
				.orElse(false)) {
			boolean isCurrentSourceInfo = state.get().getCurrentPlaybackState().get().sourceInfo.equals(this.sourceInfo.get());
			boolean sourceInfoCompleted = state.get().getCurrentPlaybackState().get().state != State.COMPLETED;
			if (isCurrentSourceInfo && sourceInfoCompleted) {
				PlaybackState playbackState = state.get().getCurrentPlaybackState().get();
				this.view.displayAsActiveSource();
				if (playbackState.getMaxTimeInMilliseconds().isPresent()) {
					int currentTime = playbackState.currentTimeInMilliseconds;
					int maxTime = playbackState.getMaxTimeInMilliseconds().get();
					this.view.setProgress(currentTime, maxTime);
				} else {
					this.view.setProgress(0, 100);
				}
			} else {
				this.view.displayAsInactiveSource();
			}
		} else {
			this.view.displayAsInactiveSource();
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

		}
	};
}
