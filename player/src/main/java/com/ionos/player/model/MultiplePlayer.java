/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model;

import com.annimon.stream.Optional;
import com.ionos.player.model.release_strategy.SourceInfoReleaseStrategy;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.util.Action;
import com.ionos.player.util.ParamAction;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public interface MultiplePlayer {

	interface Model {

		void start(Action onSuccess, ParamAction<Throwable> onError);

		void setSourceInfos(List<PlayerFileInfo> sourceInfos, SourceInfoReleaseStrategy releaseStrategy);

		void release();

		Optional<MultiplePlaybackState> getState();

		void videoViewSetter(ParamAction<VideoViewSetter> success);

		void addListener(Listener listener);

		void removeListener(Listener listener);

		void play();

		void pause();

		void stop();

		void playNext();

		void playPrevious();

		void seekToPosition(int positionInMilliseconds);

		void repeatSingle();

		void doNotRepeatSingle();

		void shuffle();

		void doNotShuffle();

		void switchToSourceInfo(PlayerFileInfo sourceInfo);

		interface Listener {
			void onUpdate(MultiplePlaybackState state);

			void onError(Throwable error);

			void onSourceInfosChanged(List<PlayerFileInfo> originalSourceInfos,
									  List<PlayerFileInfo> currentSourceInfos);
		}
	}

	interface SourcesView {

		void displayCurrentSourceInfo(PlayerFileInfo sourceInfo);

		void displaySourceInfos(List<PlayerFileInfo> sourceInfos);
	}

	interface SourcesPresenter extends BasePresenter {

		void setView(SourcesView view);

		void onSwitchToSourceInfo(PlayerFileInfo sourceInfo);
	}

	interface ErrorView {
		void showError(String message);
	}

	interface ErrorPresenter extends BasePresenter {
		void setView(ErrorView errorView);
	}

	interface ActiveSourceView {

		void displayAsActiveSource();

		void displayAsInactiveSource();

		void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds);
	}

	interface ActiveSourcePresenter extends BasePresenter {

		void setView(ActiveSourceView view);

		void setSourceInfo(PlayerFileInfo sourceInfo);
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

		void setVideoView(VideoViewSetter setter, PlayerFileInfo sourceInfo);

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
