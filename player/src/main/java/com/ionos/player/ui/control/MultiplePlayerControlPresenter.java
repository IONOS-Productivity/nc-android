/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control;

import com.annimon.stream.Optional;
import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.PlaybackStateAnalyzer;
import com.ionos.player.ui.control.availability_strategy.ControlAvailabilityStrategy;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerControlPresenter implements MultiplePlayer.ControlPresenter {

	private final MultiplePlayer.Model model;
	private final ControlAvailabilityStrategy nextControlAvailabilityStrategy;
	private final ControlAvailabilityStrategy previousControlAvailabilityStrategy;
	private MultiplePlayer.ControlView view = NullMultiplePlayerControlView.getInstance();

	public MultiplePlayerControlPresenter(
			MultiplePlayer.Model model,
			ControlAvailabilityStrategy nextControlAvailabilityStrategy,
			ControlAvailabilityStrategy previousControlAvailabilityStrategy) {
		this.model = model;
		this.nextControlAvailabilityStrategy = nextControlAvailabilityStrategy;
		this.previousControlAvailabilityStrategy = previousControlAvailabilityStrategy;
	}

	@Override
	public void setView(MultiplePlayer.ControlView view) {
		this.view = view != null ? view : NullMultiplePlayerControlView.getInstance();
	}

	@Override
	public void onCreate() {
		updateView();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerControlView.getInstance();
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
	public void onPlay() {
		this.model.play();
	}

	@Override
	public void onPause() {
		this.model.pause();
	}

	@Override
	public void onStop() {
		this.model.stop();
	}

	@Override
	public void onPlayNext() {
		this.model.playNext();
	}

	@Override
	public void onPlayPrevious() {
		this.model.playPrevious();
	}

	@Override
	public void onSeekToPosition(int positionInMilliseconds) {
		this.model.seekToPosition(positionInMilliseconds);
	}

	@Override
	public void onRepeat() {
		this.model.repeatSingle();
	}

	@Override
	public void onDoNotRepeat() {
		this.model.doNotRepeatSingle();
	}

	@Override
	public void onShuffle() {
		this.model.shuffle();
	}

	@Override
	public void onDoNotShuffle() {
		this.model.doNotShuffle();
	}

	private void updateView() {
		updateView(this.model.getState());
	}

	private void updateView(Optional<MultiplePlaybackState> state) {
		boolean repeatSingle = false;
		boolean shuffle = false;

		if (state.isPresent()) {
			repeatSingle = state.get().repeatSingle;
			shuffle = state.get().shuffle;
		}

		if (repeatSingle) {
			this.view.repeat();
		} else {
			this.view.doNotRepeat();
		}

		if (shuffle) {
			this.view.shuffle();
		} else {
			this.view.doNotShuffle();
		}

		if (state
				.mapToBoolean(input -> input.getCurrentPlaybackState().isPresent())
				.orElse(false)) {
			PlaybackState playbackState = state.get().getCurrentPlaybackState().get();
			PlaybackStateAnalyzer analyzer = new PlaybackStateAnalyzer(playbackState);

			this.view.enablePlayControls(
					analyzer.playAvailable(),
					analyzer.pauseAvailable(),
					analyzer.stopAvailable());
			this.view.enableSwitchControls(
					this.nextControlAvailabilityStrategy.available(
							state.get().currentSourceInfos,
							state.get().getCurrentPlaybackState().get().sourceInfo,
							state.get().shuffle),
					this.previousControlAvailabilityStrategy.available(
							state.get().currentSourceInfos,
							state.get().getCurrentPlaybackState().get().sourceInfo,
							state.get().shuffle));

			if (playbackState.getMaxTimeInMilliseconds().isPresent()) {
				int currentTime = playbackState.currentTimeInMilliseconds;
				int maxTime = playbackState.getMaxTimeInMilliseconds().get();
				this.view.setProgressAvailable();
				this.view.setProgress(currentTime, maxTime);
			} else {
				this.view.setProgressNotAvailable();
			}
		} else {
			this.view.enablePlayControls(false, false, false);
			this.view.enableSwitchControls(false, false);
			this.view.setProgressNotAvailable();
		}
	}

	private final MultiplePlayer.Model.Listener listener = new MultiplePlayer.Model.Listener() {
		@Override
		public void onUpdate(MultiplePlaybackState state) {
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
