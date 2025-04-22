/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.presenter;

import com.annimon.stream.Optional;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.null_object.NullMultiplePlayerActiveSourceView;
import com.ionos.player.player.interfaces.PlaybackState;
import com.ionos.player.player.interfaces.State;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerActiveSourcePresenter<SourceInfo, Mode> implements MultiplePlayer.ActiveSourcePresenter<SourceInfo> {

	private final MultiplePlayer.Model<SourceInfo, Mode> model;
	private Optional<SourceInfo> sourceInfo = Optional.empty();
	private MultiplePlayer.ActiveSourceView<SourceInfo> view = NullMultiplePlayerActiveSourceView.getInstance();

	public MultiplePlayerActiveSourcePresenter(MultiplePlayer.Model<SourceInfo, Mode> model) {
		this.model = model;
	}

	@Override
	public void setSourceInfo(SourceInfo sourceInfo) {
		this.sourceInfo = Optional.of(sourceInfo);
		updateView();
	}

	@Override
	public void setView(MultiplePlayer.ActiveSourceView<SourceInfo> view) {
		this.view = view != null ? view : NullMultiplePlayerActiveSourceView.<SourceInfo>getInstance();
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

	private void updateView(Optional<MultiplePlaybackState<SourceInfo, Mode>> state) {
		if (this.sourceInfo.isPresent() && state
				.mapToBoolean(input -> input.getCurrentPlaybackState().isPresent())
				.orElse(false)) {
			boolean isCurrentSourceInfo = state.get().getCurrentPlaybackState().get().sourceInfo.equals(this.sourceInfo.get());
			boolean sourceInfoCompleted = state.get().getCurrentPlaybackState().get().state != State.COMPLETED;
			if (isCurrentSourceInfo && sourceInfoCompleted) {
				PlaybackState<SourceInfo> playbackState = state.get().getCurrentPlaybackState().get();
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

	private final MultiplePlayer.Model.Listener<SourceInfo, Mode> listener = new MultiplePlayer.Model.Listener<SourceInfo, Mode>() {
		@Override
		public void onUpdate(MultiplePlaybackState<SourceInfo, Mode> state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<SourceInfo> originalSourceInfos, List<SourceInfo> currentSourceInfos) {

		}
	};
}
