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
import com.ionos.player.ui.MultiplePlayer;
import com.ionos.player.ui.sources.destroy_strategy.MultiplePlayerPresenterDestroyStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiplePlayerSourcesPresenter implements MultiplePlayer.SourcesPresenter {

	private final PlaybackModel model;
    private MultiplePlayer.SourcesView view = NullMultiplePlayerSourcesView.getInstance();

	private final MultiplePlayerPresenterDestroyStrategy destroyStrategy;

	public MultiplePlayerSourcesPresenter(
			PlaybackModel model,
            MultiplePlayerPresenterDestroyStrategy destroyStrategy) {
		this.model = model;
		this.destroyStrategy = destroyStrategy;
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
	public void onSwitchToFile(PlaybackFile file) {
		this.model.switchToFile(file);
	}

	private void updateView() {
		updateView(this.model.getState());
	}

	private void updateView(Optional<PlaybackState> state) {
		List<PlaybackFile> sources = new ArrayList<>();

		if (state.isPresent()) {
			sources = state.get().currentFiles;
		}

		this.view.displayFiles(sources);
		if (state.map(input -> input.currentItemState.isPresent()).orElse(false)) {
			PlaybackItemState playbackItemState = state.get().currentItemState.get();
			this.view.displayCurrentFile(playbackItemState.file);
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
			view.displayFiles(currentFiles);
		}
	};
}
