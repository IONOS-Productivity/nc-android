/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.util;

import android.os.Handler;

public class PeriodicAction {

    private final Handler handler = new Handler();
    private final int periodicIntervalInMilliseconds;
    private final Action action;

    public PeriodicAction(int periodicIntervalInMilliseconds, Action action) {
        this.periodicIntervalInMilliseconds = periodicIntervalInMilliseconds;
        this.action = action;
    }

    public void start() {
        stop();
        this.handler.postDelayed(this.runnable, this.periodicIntervalInMilliseconds);
    }

    public void stop() {
        this.handler.removeCallbacks(this.runnable);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            action.execute();
            start();
        }
    };
}
