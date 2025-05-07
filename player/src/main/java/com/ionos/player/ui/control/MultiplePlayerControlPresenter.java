/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control;

import com.annimon.stream.Optional;
import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.PlaybackStateAnalyzer;
import com.ionos.player.model.volume.VolumeChangedListener;
import com.ionos.player.ui.control.availability_strategy.ControlAvailabilityStrategy;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlayerControlPresenter<SourceInfo> implements MultiplePlayer.ControlPresenter<SourceInfo> {

	private final MultiplePlayer.Model<SourceInfo> model;
	private final ControlAvailabilityStrategy<SourceInfo> nextControlAvailabilityStrategy;
	private final ControlAvailabilityStrategy<SourceInfo> previousControlAvailabilityStrategy;
	private MultiplePlayer.ControlView<SourceInfo> view = NullMultiplePlayerControlView.getInstance();
	private double currentVolume = 0;

	public MultiplePlayerControlPresenter(
			MultiplePlayer.Model<SourceInfo> model,
			ControlAvailabilityStrategy<SourceInfo> nextControlAvailabilityStrategy,
			ControlAvailabilityStrategy<SourceInfo> previousControlAvailabilityStrategy) {
		this.model = model;
		this.nextControlAvailabilityStrategy = nextControlAvailabilityStrategy;
		this.previousControlAvailabilityStrategy = previousControlAvailabilityStrategy;
	}

	@Override
	public void setView(MultiplePlayer.ControlView<SourceInfo> view) {
		this.view = view != null ? view : NullMultiplePlayerControlView.<SourceInfo>getInstance();
	}

	@Override
	public void onCreate() {
		updateView();
		configureCurrentVolume();
	}

	@Override
	public void onDestroy() {
		this.view = NullMultiplePlayerControlView.getInstance();
	}

	private void configureCurrentVolume() {
		this.currentVolume = this.model.getCurrentVolume();
		this.model.setVolume(this.currentVolume);
		updateVolumeUi();
	}

	@Override
	public void onAppear() {
		updateView();
		this.model.addListener(this.listener);
		this.model.addVolumeChangedListener(this.volumeChangedListener);
		this.model.startTrackingVolumeEvents();
	}

	@Override
	public void onDisappear() {
		this.model.removeListener(this.listener);
		this.model.removeVolumeChangedListener(this.volumeChangedListener);
		this.model.stopTrackingVolumeEvents();
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

	@Override
	public void onVolumeChanged(double volume) {
		this.currentVolume = volume;
		this.model.setVolume(volume);
		updateVolumeUi();
	}

	private boolean isMuted() {
		return this.currentVolume == 0;
	}

	@Override
	public void onToggleMute() {
		this.currentVolume = isMuted() ? model.getPreviousVolume() : 0;
		this.model.setVolume(this.currentVolume);
		updateVolumeUi();
	}

	private void updateVolumeUi() {
		this.view.setCurrentVolume(this.model.getCurrentVolume());
	}

	private void updateView() {
		updateView(this.model.getState());
	}

	private void updateView(Optional<MultiplePlaybackState<SourceInfo>> state) {
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
			PlaybackState<SourceInfo> playbackState = state.get().getCurrentPlaybackState().get();
			PlaybackStateAnalyzer<SourceInfo> analyzer = new PlaybackStateAnalyzer<>(playbackState);

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

	private final MultiplePlayer.Model.Listener<SourceInfo> listener = new MultiplePlayer.Model.Listener<>() {
		@Override
		public void onUpdate(MultiplePlaybackState state) {
			updateView();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<SourceInfo> originalSourceInfos, List<SourceInfo> currentSourceInfos) {

		}
	};

	private final VolumeChangedListener volumeChangedListener = new VolumeChangedListener() {
		@Override
		public void onVolumeChange(double previousVolume) {
			updateVolumeUi();
		}
	};
}
