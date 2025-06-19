/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control.listener;

import android.os.Handler;
import android.view.View;

import java.util.Optional;

public abstract class MultipleClickListener implements View.OnClickListener {

    private static final int TIME_WINDOW_FOR_CLICK_DETERMINATION_IN_MILLISECONDS = 250;

    private final Handler handler = new Handler();
    private Optional<Integer> clicksCount = Optional.empty();

    protected abstract void onSingleClick(View view);

    protected abstract void onDoubleClick(View view);

    @Override
    public final void onClick(final View view) {
        boolean interactionIsBegan = clicksCount.isPresent();

        if (interactionIsBegan) {
            clicksCount = Optional.of(clicksCount.get() + 1);
        } else {
            clicksCount = Optional.of(1);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int count = clicksCount.get();
                    clicksCount = Optional.empty();
                    callSubscriber(view, count);
                }
            }, TIME_WINDOW_FOR_CLICK_DETERMINATION_IN_MILLISECONDS);
        }
    }

    private void callSubscriber(View view, int clicksCount) {
        if (clicksCount == 1) {
            onSingleClick(view);
        } else {
            onDoubleClick(view);
        }
    }
}
