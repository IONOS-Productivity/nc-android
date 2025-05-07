package com.ionos.player.ui.control;

import com.annimon.stream.Optional;
import com.ionos.player.model.MultiplePlayer;
import com.ionos.player.model.state.MultiplePlaybackState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.State;

/**
 * Created by yaz on 1/25/17.
 */

public class MultiplePlayerPlayPreviousPresenterImpl<SourceInfo> implements MultiplePlayerPlayPreviousPresenter {

	private final MultiplePlayer.Model<SourceInfo> playerModel;
	private final MultiplePlayer.ControlPresenter<SourceInfo> controlPresenter;

	public MultiplePlayerPlayPreviousPresenterImpl(MultiplePlayer.Model<SourceInfo> playerModel, MultiplePlayer.ControlPresenter<SourceInfo> controlPresenter) {
		this.playerModel = playerModel;
		this.controlPresenter = controlPresenter;
	}

	@Override
	public void onPreviousClicked() {
		PlaybackState<SourceInfo> state = findState();
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
		PlaybackState<SourceInfo> state = findState();
		if (state == null) {
			return;
		}
		this.controlPresenter.onPlayPrevious();
		if (state.state != State.PAUSED && state.state != State.PLAYING) {
			this.controlPresenter.onPlayPrevious();
		}
	}

	private PlaybackState<SourceInfo> findState() {
		Optional<MultiplePlaybackState<SourceInfo>> multiplePlaybackStateOptional = this.playerModel.getState();
		if (multiplePlaybackStateOptional.isPresent()) {
			MultiplePlaybackState<SourceInfo> multiplePlaybackState = multiplePlaybackStateOptional.get();
			Optional<PlaybackState<SourceInfo>> playbackStateOptional = multiplePlaybackState.getCurrentPlaybackState();
			if (playbackStateOptional.isPresent()) {
				return playbackStateOptional.get();
			}
		}
		return null;
	}
}
