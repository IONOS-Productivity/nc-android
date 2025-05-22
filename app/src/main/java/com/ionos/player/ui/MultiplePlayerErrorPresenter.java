/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.ui.message.ExceptionToMessageTransformation;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerErrorPresenter implements MultiplePlayer.ErrorPresenter {
	private MultiplePlayer.ErrorView errorView = NullMultiplePlayerErrorView.getInstance();
	private final PlaybackModel model;
	private final ExceptionToMessageTransformation exceptionToMessageTransformation;

	public MultiplePlayerErrorPresenter(
			PlaybackModel model,
            ExceptionToMessageTransformation exceptionToMessageTransformation) {
		this.model = model;
		this.exceptionToMessageTransformation = exceptionToMessageTransformation;
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
			errorView.showError(exceptionToMessageTransformation.transform(error));
		}

		@Override
		public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) {

		}
	};
}
