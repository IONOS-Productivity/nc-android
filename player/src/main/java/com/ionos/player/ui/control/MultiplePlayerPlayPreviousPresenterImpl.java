package com.ionos.player.ui.control;

import com.annimon.stream.Optional;
import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.State;

/**
 * Created by yaz on 1/25/17.
 */

public class MultiplePlayerPlayPreviousPresenterImpl implements MultiplePlayerPlayPreviousPresenter {

	private final MultiplePlayer.Model playerModel;
	private final MultiplePlayer.ControlPresenter controlPresenter;

	public MultiplePlayerPlayPreviousPresenterImpl(MultiplePlayer.Model playerModel, MultiplePlayer.ControlPresenter controlPresenter) {
		this.playerModel = playerModel;
		this.controlPresenter = controlPresenter;
	}

	@Override
	public void onPreviousClicked() {
		PlaybackState state = findState();
		if (state == null) {
			return;
		}
		if (state.state == State.PAUSED || state.state == State.PLAYING) {
			this.controlPresenter.onSeekToPosition(0);
		} else {
			this.controlPresenter.onPlayPrevious();
		}
	}

	@Override
	public void onPreviousDoubleClicked() {
		PlaybackState state = findState();
		if (state == null) {
			return;
		}
		this.controlPresenter.onPlayPrevious();
		if (state.state != State.PAUSED && state.state != State.PLAYING) {
			this.controlPresenter.onPlayPrevious();
		}
	}

	private PlaybackState findState() {
		Optional<MultiplePlaybackState> multiplePlaybackStateOptional = this.playerModel.getState();
		if (multiplePlaybackStateOptional.isPresent()) {
			MultiplePlaybackState multiplePlaybackState = multiplePlaybackStateOptional.get();
			Optional<PlaybackState> playbackStateOptional = multiplePlaybackState.getCurrentPlaybackState();
			if (playbackStateOptional.isPresent()) {
				return playbackStateOptional.get();
			}
		}
		return null;
	}
}
