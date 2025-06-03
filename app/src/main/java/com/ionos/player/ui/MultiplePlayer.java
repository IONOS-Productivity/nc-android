/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.VideoViewSetter;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public interface MultiplePlayer {

	interface SourcesView {

		void displayCurrentFile(PlaybackFile file);

		void displayFiles(List<PlaybackFile> files);
	}

	interface SourcesPresenter extends BasePresenter {

		void setView(SourcesView view);

		void onSwitchToFile(PlaybackFile file);
	}

	interface ErrorView {
		void showError(int messageId);
	}

	interface ErrorPresenter extends BasePresenter {
		void setView(ErrorView errorView);
	}

	interface ControlView {
		void repeat();

		void doNotRepeat();

		void shuffle();

		void doNotShuffle();

		void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds);

		void setProgressAvailable();

		void setProgressNotAvailable();

		void enablePlayControls(boolean play, boolean pause, boolean stop);

		void enableSwitchControls(boolean next, boolean previous);
	}

	interface ControlPresenter extends BasePresenter {

		void setView(ControlView view);

		void onPlay();

		void onPause();

		void onStop();

		void onPlayNext();

		void onPlayPrevious();

		void onSeekToPosition(int positionInMilliseconds);

		void onRepeat();

		void onDoNotRepeat();

		void onShuffle();

		void onDoNotShuffle();

	}

	interface VideoView {

		void setVideoViewAvailable();

		void setVideoViewUnavailable();

		void setVideoView(VideoViewSetter setter, PlaybackFile file);

		void clearVideoView(VideoViewSetter setter);
	}

	interface VideoPresenter extends BasePresenter {

		void setView(VideoView view);

		void onVideoViewCreated();

		void onVideoViewDestroyed();
	}

	interface HidingView {

		void displayPlayerView();

		void doNotDisplayPlayerView();

	}

	interface HidingPresenter extends BasePresenter {

		void setView(HidingView view);

	}

	interface BasePresenter {

		void onCreate();

		void onDestroy();

		void onAppear();

		void onDisappear();
	}
}
