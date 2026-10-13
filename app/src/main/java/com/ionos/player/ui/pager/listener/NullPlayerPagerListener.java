/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.pager.listener;

public class NullPlayerPagerListener<T> implements PlayerPagerListener<T> {

    private static final NullPlayerPagerListener INSTANCE = new NullPlayerPagerListener();

    public static <T> NullPlayerPagerListener<T> getInstance() {
        return INSTANCE;
    }

    @Override
    public void onSwitchToItem(T item) {
    }
}
