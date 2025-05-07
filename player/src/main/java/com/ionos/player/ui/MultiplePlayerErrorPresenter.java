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
import com.ionos.player.ui.message.ExceptionToMessageTransformation;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerErrorPresenter implements MultiplePlayer.ErrorPresenter {
	private MultiplePlayer.ErrorView errorView = NullMultiplePlayerErrorView.getInstance();
	private final MultiplePlayer.Model model;
	private final ExceptionToMessageTransformation exceptionToMessageTransformation;

	public MultiplePlayerErrorPresenter(
			MultiplePlayer.Model model,
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

	private final MultiplePlayer.Model.Listener listener = new MultiplePlayer.Model.Listener() {
		@Override
		public void onUpdate(MultiplePlaybackState state) {

		}

		@Override
		public void onError(Throwable error) {
			errorView.showError(exceptionToMessageTransformation.transform(error));
		}

		@Override
		public void onSourceInfosChanged(List<PlayerFileInfo> originalSourceInfos, List<PlayerFileInfo> currentSourceInfos) {

		}
	};
}
