/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui;

public class NullMultiplePlayerHidingView implements MultiplePlayer.HidingView {

    private static final NullMultiplePlayerHidingView INSTANCE = new NullMultiplePlayerHidingView();

    public static NullMultiplePlayerHidingView getInstance() {
        return INSTANCE;
    }

    private NullMultiplePlayerHidingView() {
    }

    @Override
    public void displayPlayerView() {

    }

    @Override
    public void doNotDisplayPlayerView() {

    }
}
