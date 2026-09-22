/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control.listener;

public interface PlayerControlViewListener {

    void onNextClicked();

    void onPreviousClicked();

    void onPlayClicked();

    void onPauseClicked();

    void onRepeatClicked();

    void onDoNotRepeatClicked();

    void onShuffleClicked();

    void onDoNotShuffleClicked();

    void onProgressChangedByUser(int progress);

    void onProgressStopTrackingTouch();
}
