/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.util;

public class TimeSkippableActionExecutor {

    private static final int DEFAULT_ALLOWED_REPEAT_DELAY = 1000;
    private double lastExecutionTime;

    public void execute(Action action) {
        execute(action, DEFAULT_ALLOWED_REPEAT_DELAY);
    }

    public void execute(Action action, int allowedRepeatDelay) {
        long currentMillis = System.currentTimeMillis();
        if (currentMillis > this.lastExecutionTime + allowedRepeatDelay) {
            this.lastExecutionTime = currentMillis;
            action.execute();
        }
    }
}
