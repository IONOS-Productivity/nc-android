/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control;

import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.PlayerState;
import com.ionos.player.ui.MultiplePlayer;

import java.util.Optional;

public class MultiplePlayerPlayPreviousPresenterImpl implements MultiplePlayerPlayPreviousPresenter {

    private final PlaybackModel playerModel;
    private final MultiplePlayer.ControlPresenter controlPresenter;

    public MultiplePlayerPlayPreviousPresenterImpl(PlaybackModel playerModel, MultiplePlayer.ControlPresenter controlPresenter) {
        this.playerModel = playerModel;
        this.controlPresenter = controlPresenter;
    }

    @Override
    public void onPreviousClicked() {
        PlaybackItemState state = findState();
        if (state == null) {
            return;
        }
        if (state.playerState == PlayerState.PAUSED || state.playerState == PlayerState.PLAYING) {
            this.controlPresenter.onSeekToPosition(0);
        } else {
            this.controlPresenter.onPlayPrevious();
        }
    }

    @Override
    public void onPreviousDoubleClicked() {
        PlaybackItemState state = findState();
        if (state == null) {
            return;
        }
        this.controlPresenter.onPlayPrevious();
        if (state.playerState != PlayerState.PAUSED && state.playerState != PlayerState.PLAYING) {
            this.controlPresenter.onPlayPrevious();
        }
    }

    private PlaybackItemState findState() {
        Optional<PlaybackState> playbackStateOptional = this.playerModel.getState();
        if (playbackStateOptional.isPresent()) {
            PlaybackState playbackState = playbackStateOptional.get();
            Optional<PlaybackItemState> playbackItemStateOptional = playbackState.currentItemState;
            if (playbackItemStateOptional.isPresent()) {
                return playbackItemStateOptional.get();
            }
        }
        return null;
    }
}
