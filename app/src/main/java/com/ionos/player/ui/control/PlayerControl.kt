/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control

import com.ionos.player.ui.common.PlayerBasePresenter

interface PlayerControl {

    interface View {

        fun repeat()

        fun doNotRepeat()

        fun shuffle()

        fun doNotShuffle()

        fun setProgress(currentTimeInMilliseconds: Int, totalTimeInMilliseconds: Int)

        fun setProgressAvailable()

        fun setProgressNotAvailable()

        fun enablePlayControls(play: Boolean, pause: Boolean, stop: Boolean)

        fun enableSwitchControls(next: Boolean, previous: Boolean)
    }

    interface Presenter : PlayerBasePresenter {

        fun setView(view: View?)

        fun onPlay()

        fun onPause()

        fun onStop()

        fun onNextClicked()

        fun onPreviousClicked()

        fun onPreviousDoubleClicked()

        fun onSeekToPosition(positionInMilliseconds: Int)

        fun onRepeat()

        fun onDoNotRepeat()

        fun onShuffle()

        fun onDoNotShuffle()
    }
}
