/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.video;

import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.ui.MultiplePlayer;

import java.util.List;

import kotlin.Unit;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerVideoPresenter implements MultiplePlayer.VideoPresenter {

	private final PlaybackModel model;
	private final PlayerFileInfo sourceInfo;
	private MultiplePlayer.VideoView view = NullMultiplePlayerVideoView.getInstance();
	private boolean appeared;

	public MultiplePlayerVideoPresenter(
			PlaybackModel model,
			PlayerFileInfo sourceInfo) {
		this.model = model;
		this.sourceInfo = sourceInfo;
	}

	@Override
	public void setView(MultiplePlayer.VideoView view) {
		this.view = view != null ? view : NullMultiplePlayerVideoView.getInstance();
	}

	@Override
	public void onCreate() {
		updateView();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerVideoView.getInstance();
	}

	@Override
	public void onAppear() {
		this.appeared = true;
		updateView();
		this.model.addListener(this.listener);
	}

	@Override
	public void onDisappear() {
		this.model.removeListener(this.listener);
		this.appeared = false;
		updateView();
	}

	@Override
	public void onVideoViewCreated() {
		updateView();
	}

	@Override
	public void onVideoViewDestroyed() {
		updateView();
	}

	private void updateView() {
		if (isCurrentSourceInfo()) {
			if (this.appeared) {
				this.view.setVideoViewAvailable();
				this.model.videoViewSetter(value -> {
					MultiplePlayerVideoPresenter.this.view.setVideoView(value, sourceInfo);
					return Unit.INSTANCE;
				});
			} else {
				this.view.setVideoViewUnavailable();
				this.model.videoViewSetter(value -> {
					MultiplePlayerVideoPresenter.this.view.clearVideoView(value);
					return Unit.INSTANCE;
				});
			}
		} else {
			this.view.setVideoViewUnavailable();
		}
	}

	private boolean isCurrentSourceInfo() {
		if (this.model.getState().isPresent()) {
			PlaybackState modelState = this.model.getState().get();
			if (modelState.currentPlaybackItemState.isPresent()) {
				PlaybackItemState playbackItemState = modelState.currentPlaybackItemState.get();
				if (this.sourceInfo.equals(playbackItemState.sourceInfo)) {
					return true;
				}
			}
		}
		return false;
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

		}
	};
}
