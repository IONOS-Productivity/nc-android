/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control.listener;

import java.util.HashSet;
import java.util.Set;

public class PlayerControlViewCompositeListener implements PlayerControlViewListener {

    private final Set<PlayerControlViewListener> listeners = new HashSet<>();

    public void addListener(PlayerControlViewListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(PlayerControlViewListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void onNextClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onNextClicked();
        }
    }

    @Override
    public void onPreviousClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onPreviousClicked();
        }
    }

    @Override
    public void onPlayClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onPlayClicked();
        }
    }

    @Override
    public void onPauseClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onPauseClicked();
        }
    }

    @Override
    public void onRepeatClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onRepeatClicked();
        }
    }

    @Override
    public void onDoNotRepeatClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onDoNotRepeatClicked();
        }
    }

    @Override
    public void onShuffleClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onShuffleClicked();
        }
    }

    @Override
    public void onDoNotShuffleClicked() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onDoNotShuffleClicked();
        }
    }

    @Override
    public void onProgressChangedByUser(int progress) {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onProgressChangedByUser(progress);
        }
    }

    @Override
    public void onProgressStopTrackingTouch() {
        for (PlayerControlViewListener listener : this.listeners) {
            listener.onProgressStopTrackingTouch();
        }
    }
}
