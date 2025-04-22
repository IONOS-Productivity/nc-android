/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.presenter;

import com.annimon.stream.Optional;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayer.interfaces.SourceInfoReleaseStrategy;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayerPresenterDestroyStrategy;
import com.ionos.player.multipleplayermvp.null_object.NullMultiplePlayerSourcesView;
import com.ionos.player.player.interfaces.PlaybackState;
import com.ionos.player.player.interfaces.PlayerExceptionMessageProvider;
import com.ionos.player.player.transformation.ExceptionToMessageTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerSourcesPresenter<SourceInfo, Mode> implements MultiplePlayer.SourcesPresenter<SourceInfo> {

	private final MultiplePlayer.Model<SourceInfo, Mode> model;
	private final ExceptionToMessageTransformation exceptionToMessageTransformation;
	private MultiplePlayer.SourcesView<SourceInfo> view = NullMultiplePlayerSourcesView.getInstance();

	private final MultiplePlayerPresenterDestroyStrategy<SourceInfo, Mode> destroyStrategy;
	private final SourceInfoReleaseStrategy<SourceInfo> releaseStrategy;

	public MultiplePlayerSourcesPresenter(
			MultiplePlayer.Model<SourceInfo, Mode> model,
			MultiplePlayerPresenterDestroyStrategy<SourceInfo, Mode> destroyStrategy,
			SourceInfoReleaseStrategy<SourceInfo> releaseStrategy,
			PlayerExceptionMessageProvider exceptionMessageProvider) {
		this.model = model;
		this.destroyStrategy = destroyStrategy;
		this.releaseStrategy = releaseStrategy;
		this.exceptionToMessageTransformation = new ExceptionToMessageTransformation(exceptionMessageProvider);
	}

	@Override
	public void setView(MultiplePlayer.SourcesView<SourceInfo> view) {
		this.view = view != null ? view : NullMultiplePlayerSourcesView.<SourceInfo>getInstance();
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
	public void onSwitchToSourceInfo(SourceInfo sourceInfo) {
		this.model.switchToSourceInfo(sourceInfo);
	}

	private void updateView() {
		updateView(this.model.getState());
	}

	private void updateView(Optional<MultiplePlaybackState<SourceInfo, Mode>> state) {
		List<SourceInfo> sources = new ArrayList<>();

		if (state.isPresent()) {
			sources = state.get().currentSourceInfos;
		}

		this.view.displaySourceInfos(sources);
		if (state.mapToBoolean(input -> input.getCurrentPlaybackState().isPresent()).orElse(false)) {
			PlaybackState<SourceInfo> playbackState = state.get().getCurrentPlaybackState().get();
			this.view.displayCurrentSourceInfo(playbackState.sourceInfo);
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
			view.displaySourceInfos(currentSourceInfos);
		}
	};
}
