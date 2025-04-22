/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.video;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.player.interfaces.VideoViewSetter;

/**
 * User: zuzik
 * Date: 8/13/16
 */
public class SurfaceVideoView<SourceInfo> implements MultiplePlayer.VideoView<SourceInfo> {

	private final SurfaceHolder holder;
	private final MultiplePlayer.VideoPresenter<SourceInfo> presenter;
	private Strategy<SourceInfo> strategy = new UnavailableHolderStrategy();

	public SurfaceVideoView(SurfaceView view, MultiplePlayer.VideoPresenter<SourceInfo> presenter) {
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
	public void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo) {
		this.strategy.setVideoView(setter, sourceInfo);
	}

	@Override
	public void clearVideoView(VideoViewSetter setter) {
		setter.setVideoView(null);
	}

	//endregion

	//region Strategy

	private interface Strategy<SourceInfo> {
		void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo);

		void setVideoViewAvailable();

		void setVideoViewUnavailable();
	}

	private class AvailableHolderStrategy implements Strategy<SourceInfo> {

		@Override
		public void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo) {
			setter.setVideoView(holder);
		}

		@Override
		public void setVideoViewAvailable() {
		}

		@Override
		public void setVideoViewUnavailable() {

		}
	}

	private class UnavailableHolderStrategy implements Strategy<SourceInfo> {
		@Override
		public void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo) {
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
