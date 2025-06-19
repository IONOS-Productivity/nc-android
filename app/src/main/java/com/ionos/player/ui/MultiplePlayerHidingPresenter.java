/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.state.PlaybackState;

import java.util.List;

public class MultiplePlayerHidingPresenter implements MultiplePlayer.HidingPresenter {

    private final PlaybackModel model;
    private MultiplePlayer.HidingView view = NullMultiplePlayerHidingView.getInstance();

    public MultiplePlayerHidingPresenter(PlaybackModel model) {
        this.model = model;
    }

    @Override
    public void setView(MultiplePlayer.HidingView view) {
        this.view = view != null ? view : NullMultiplePlayerHidingView.getInstance();
    }

    @Override
    public void onCreate() {
        updateView();
    }

    @Override
    public void onDestroy() {
        this.view = NullMultiplePlayerHidingView.getInstance();
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

    private void updateView() {
        boolean hasSources = this.model.getState()
            .map(input -> !input.currentFiles.isEmpty())
            .orElse(false);
        if (hasSources) {
            this.view.displayPlayerView();
        } else {
            this.view.doNotDisplayPlayerView();
        }
    }

    private final PlaybackModel.Listener listener = new PlaybackModel.Listener() {
        @Override
        public void onUpdate(PlaybackState state) {
            updateView();
        }

        @Override
        public void onError(Throwable error) {
        }

        @Override
        public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) {
            updateView();
        }
    };
}
