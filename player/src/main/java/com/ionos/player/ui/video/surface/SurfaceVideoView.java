/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.video.surface;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.VideoViewSetter;
import com.ionos.player.ui.MultiplePlayer;

/**
 * User: zuzik
 * Date: 8/13/16
 */
public class SurfaceVideoView implements MultiplePlayer.VideoView {

	private final SurfaceHolder holder;
	private final MultiplePlayer.VideoPresenter presenter;
	private Strategy strategy = new UnavailableHolderStrategy();

	public SurfaceVideoView(SurfaceView view, MultiplePlayer.VideoPresenter presenter) {
		this.holder = view.getHolder();
		this.presenter = presenter;
	}

	public void onCreate() {
		this.holder.addCallback(this.surfaceHolderCallback);
		this.presenter.onCreate();
	}

	public void onDestroy() {
		this.holder.removeCallback(this.surfaceHolderCallback);
		this.presenter.onDestroy();
	}

	public void onAppear() {
		this.presenter.onAppear();
	}

	public void onDisappear() {
		this.presenter.onDisappear();
	}

	private final SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder surfaceHolder) {
			strategy = new AvailableHolderStrategy();
			presenter.onVideoViewCreated();
		}

		@Override
		public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
			strategy = new UnavailableHolderStrategy();
			presenter.onVideoViewDestroyed();
		}
	};

	//region MultiplePlayer.VideoView

	@Override
	public void setVideoViewAvailable() {
		this.strategy.setVideoViewAvailable();
	}

	@Override
	public void setVideoViewUnavailable() {
		this.strategy.setVideoViewUnavailable();
	}

	@Override
	public void setVideoView(VideoViewSetter setter, PlayerFileInfo sourceInfo) {
		this.strategy.setVideoView(setter, sourceInfo);
	}

	@Override
	public void clearVideoView(VideoViewSetter setter) {
		setter.setVideoView(null);
	}

	//endregion

	//region Strategy

	private interface Strategy {
		void setVideoView(VideoViewSetter setter, PlayerFileInfo sourceInfo);

		void setVideoViewAvailable();

		void setVideoViewUnavailable();
	}

	private class AvailableHolderStrategy implements Strategy {

		@Override
		public void setVideoView(VideoViewSetter setter, PlayerFileInfo sourceInfo) {
			setter.setVideoView(holder);
		}

		@Override
		public void setVideoViewAvailable() {
		}

		@Override
		public void setVideoViewUnavailable() {

		}
	}

	private class UnavailableHolderStrategy implements Strategy {
		@Override
		public void setVideoView(VideoViewSetter setter, PlayerFileInfo sourceInfo) {
		}

		@Override
		public void setVideoViewAvailable() {

		}

		@Override
		public void setVideoViewUnavailable() {

		}
	}

	//endregion
}
