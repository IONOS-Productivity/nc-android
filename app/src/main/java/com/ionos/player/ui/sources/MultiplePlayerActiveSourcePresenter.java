/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.sources;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.PlayerState;
import com.ionos.player.ui.MultiplePlayer;

import java.util.List;
import java.util.Optional;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerActiveSourcePresenter implements MultiplePlayer.ActiveSourcePresenter {

	private final PlaybackModel model;
	private Optional<PlaybackFile> file = Optional.empty();
	private MultiplePlayer.ActiveSourceView view = NullMultiplePlayerActiveSourceView.getInstance();

	public MultiplePlayerActiveSourcePresenter(PlaybackModel model) {
		this.model = model;
	}

	@Override
	public void setFile(PlaybackFile file) {
		this.file = Optional.of(file);
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

	private void updateView(Optional<PlaybackState> state) {
		if (this.file.isPresent() && state
				.map(input -> input.currentItemState.isPresent())
				.orElse(false)) {
			boolean isCurrentFile = state.get().currentItemState.get().file.equals(this.file.get());
			boolean fileCompleted = state.get().currentItemState.get().playerState != PlayerState.COMPLETED;
			if (isCurrentFile && fileCompleted) {
				PlaybackItemState playbackItemState = state.get().currentItemState.get();
				this.view.displayAsActiveSource();
				if (playbackItemState.maxTimeInMilliseconds > 0) {
					int currentTime = playbackItemState.currentTimeInMilliseconds;
					int maxTime = playbackItemState.maxTimeInMilliseconds;
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

	private final PlaybackModel.Listener listener = new PlaybackModel.Listener() {
		@Override
		public void onUpdate(PlaybackState state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) {

		}
	};
}
