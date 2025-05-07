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

	interface Model<SourceInfo> {

		void start(Action onSuccess, ParamAction<Throwable> onError);

		void setSourceInfos(List<SourceInfo> sourceInfos, SourceInfoReleaseStrategy<SourceInfo> releaseStrategy);

		void release();

		Optional<MultiplePlaybackState<SourceInfo>> getState();

		void videoViewSetter(ParamAction<VideoViewSetter> success);

		void addListener(Listener<SourceInfo> listener);

		void removeListener(Listener<SourceInfo> listener);

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

		void switchToSourceInfo(SourceInfo sourceInfo);

		interface Listener<SourceInfo> {
			void onUpdate(MultiplePlaybackState<SourceInfo> state);

			void onError(Throwable error);

			void onSourceInfosChanged(List<SourceInfo> originalSourceInfos,
									  List<SourceInfo> currentSourceInfos);
		}
	}

	interface SourcesView<SourceInfo> {

		void displayCurrentSourceInfo(SourceInfo sourceInfo);

		void displaySourceInfos(List<SourceInfo> sourceInfos);
	}

	interface SourcesPresenter<SourceInfo> extends BasePresenter<SourceInfo> {

		void setView(SourcesView<SourceInfo> view);

		void onSwitchToSourceInfo(SourceInfo sourceInfo);
	}

	interface ErrorView<SourceInfo> {
		void showError(String message);
	}

	interface ErrorPresenter<SourceInfo> extends BasePresenter<SourceInfo> {
		void setView(ErrorView<SourceInfo> errorView);
	}

	interface ActiveSourceView<SourceInfo> {

		void displayAsActiveSource();

		void displayAsInactiveSource();

		void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds);
	}

	interface ActiveSourcePresenter<SourceInfo> extends BasePresenter<SourceInfo> {

		void setView(ActiveSourceView<SourceInfo> view);

		void setSourceInfo(SourceInfo sourceInfo);
	}

	interface ControlView<SourceInfo> {
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

	interface ControlPresenter<SourceInfo> extends BasePresenter<SourceInfo> {

		void setView(ControlView<SourceInfo> view);

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

	interface VideoView<SourceInfo> {

		void setVideoViewAvailable();

		void setVideoViewUnavailable();

		void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo);

		void clearVideoView(VideoViewSetter setter);
	}

	interface VideoPresenter<SourceInfo> extends BasePresenter<SourceInfo> {

		void setView(VideoView<SourceInfo> view);

		void onVideoViewCreated();

		void onVideoViewDestroyed();
	}

	interface HidingView<SourceInfo> {

		void displayPlayerView();

		void doNotDisplayPlayerView();

	}

	interface HidingPresenter<SourceInfo> extends BasePresenter<SourceInfo> {

		void setView(HidingView<SourceInfo> view);

	}

	interface BasePresenter<SourceInfo> {

		void onCreate();

		void onDestroy();

		void onAppear();

		void onDisappear();
	}
}
