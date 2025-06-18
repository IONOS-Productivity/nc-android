/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.SourceException;
import com.ionos.player.model.state.PlaybackState;
import com.owncloud.android.R;

import java.util.List;

public class MultiplePlayerErrorPresenter implements MultiplePlayer.ErrorPresenter {
	private MultiplePlayer.ErrorView errorView = NullMultiplePlayerErrorView.getInstance();
	private final PlaybackModel model;

	public MultiplePlayerErrorPresenter(PlaybackModel model) {
		this.model = model;
	}

	@Override
	public void setView(MultiplePlayer.ErrorView errorView) {
		this.errorView = errorView != null ? errorView : NullMultiplePlayerErrorView.getInstance();
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDestroy() {
		this.errorView = NullMultiplePlayerErrorView.getInstance();
	}

	@Override
	public void onAppear() {
		this.model.addListener(this.listener);
	}

	@Override
	public void onDisappear() {
		this.model.removeListener(this.listener);
	}

	private final PlaybackModel.Listener listener = new PlaybackModel.Listener() {
		@Override
		public void onUpdate(PlaybackState state) {

		}

		@Override
		public void onError(Throwable error) {
			if (error instanceof SourceException) {
				errorView.showError(R.string.player_error_source_not_found);
			} else {
				errorView.showError(R.string.player_error_unknown);
			}
		}

		@Override
		public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) {

		}
	};
}
