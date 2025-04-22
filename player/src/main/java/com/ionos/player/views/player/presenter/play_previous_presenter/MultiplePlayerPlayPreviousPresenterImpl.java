package com.ionos.player.views.player.presenter.play_previous_presenter;

import com.annimon.stream.Optional;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.player.interfaces.PlaybackState;
import com.ionos.player.player.interfaces.State;

/**
 * Created by yaz on 1/25/17.
 */

public class MultiplePlayerPlayPreviousPresenterImpl<SourceInfo, Mode> implements MultiplePlayerPlayPreviousPresenter {

	private final MultiplePlayer.Model<SourceInfo, Mode> playerModel;
	private final MultiplePlayer.ControlPresenter<SourceInfo> controlPresenter;

	public MultiplePlayerPlayPreviousPresenterImpl(MultiplePlayer.Model<SourceInfo, Mode> playerModel, MultiplePlayer.ControlPresenter<SourceInfo> controlPresenter) {
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
		Optional<MultiplePlaybackState<SourceInfo, Mode>> multiplePlaybackStateOptional = this.playerModel.getState();
		if (multiplePlaybackStateOptional.isPresent()) {
			MultiplePlaybackState<SourceInfo, Mode> multiplePlaybackState = multiplePlaybackStateOptional.get();
			Optional<PlaybackState<SourceInfo>> playbackStateOptional = multiplePlaybackState.getCurrentPlaybackState();
			if (playbackStateOptional.isPresent()) {
				return playbackStateOptional.get();
			}
		}
		return null;
	}
}
