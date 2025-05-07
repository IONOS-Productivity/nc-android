/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.transformation.Transformation;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerErrorPresenter<SourceInfo> implements MultiplePlayer.ErrorPresenter<SourceInfo> {
	private MultiplePlayer.ErrorView errorView = NullMultiplePlayerErrorView.getInstance();
	private final MultiplePlayer.Model<SourceInfo> model;
	private final Transformation<Throwable, String> exceptionToMessageTransformation;

	public MultiplePlayerErrorPresenter(
			MultiplePlayer.Model<SourceInfo> model,
			Transformation<Throwable, String> exceptionToMessageTransformation) {
		this.model = model;
		this.exceptionToMessageTransformation = exceptionToMessageTransformation;
	}

	@Override
	public void setView(MultiplePlayer.ErrorView<SourceInfo> errorView) {
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

	private final MultiplePlayer.Model.Listener<SourceInfo> listener = new MultiplePlayer.Model.Listener<>() {
		@Override
		public void onUpdate(MultiplePlaybackState<SourceInfo> state) {

		}

		@Override
		public void onError(Throwable error) {
			errorView.showError(exceptionToMessageTransformation.transform(error));
		}

		@Override
		public void onSourceInfosChanged(List<SourceInfo> originalSourceInfos, List<SourceInfo> currentSourceInfos) {

		}
	};
}
