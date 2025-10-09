/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.control

import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.state.PlaybackItemState
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.PlayerState
import com.ionos.player.model.state.PlayerStateAnalyzer
import com.ionos.player.model.state.RepeatMode
import kotlin.jvm.optionals.getOrNull

class PlayerControlPresenter(
    private val model: PlaybackModel
) : PlayerControl.Presenter {
    private var view: PlayerControl.View? = null

    private val currentPlaybackItemState: PlaybackItemState?
        get() = model.state.getOrNull()?.currentItemState

    override fun setView(view: PlayerControl.View?) {
        this.view = view
    }

    override fun onCreate() {
        updateView()
    }

    override fun onDestroy() {
        view = null
    }

    override fun onAppear() {
        updateView()
        model.addListener(listener)
    }

    override fun onDisappear() {
        model.removeListener(listener)
    }

    override fun onPlay() {
        model.play()
    }

    override fun onPause() {
        model.pause()
    }

    override fun onStop() {
        model.stop()
    }

    override fun onNextClicked() {
        model.playNext()
    }

    override fun onPreviousClicked() {
        val state = currentPlaybackItemState ?: return
        if (state.playerState == PlayerState.PAUSED || state.playerState == PlayerState.PLAYING) {
            model.seekToPosition(0)
        } else {
            model.playPrevious()
        }
    }

    override fun onPreviousDoubleClicked() {
        val state = currentPlaybackItemState ?: return
        model.playPrevious()
        if (state.playerState != PlayerState.PAUSED && state.playerState != PlayerState.PLAYING) {
            model.playPrevious()
        }
    }

    override fun onSeekToPosition(positionInMilliseconds: Int) {
        model.seekToPosition(positionInMilliseconds)
    }

    override fun onRepeat() {
        model.setRepeatMode(RepeatMode.SINGLE)
    }

    override fun onDoNotRepeat() {
        model.setRepeatMode(RepeatMode.ALL)
    }

    override fun onShuffle() {
        model.setShuffle(true)
    }

    override fun onDoNotShuffle() {
        model.setShuffle(false)
    }

    private fun updateView() {
        val state = model.state

        var repeatSingle = false
        var shuffle = false

        if (state.isPresent) {
            repeatSingle = state.get().repeatMode == RepeatMode.SINGLE
            shuffle = state.get().shuffle
        }

        if (repeatSingle) {
            view?.repeat()
        } else {
            view?.doNotRepeat()
        }

        if (shuffle) {
            view?.shuffle()
        } else {
            view?.doNotShuffle()
        }

        val playbackState = state.getOrNull()?.currentItemState
        if (playbackState != null) {
            val analyzer = PlayerStateAnalyzer(playbackState.playerState)

            view?.enablePlayControls(
                analyzer.playAvailable(),
                analyzer.pauseAvailable(),
                analyzer.stopAvailable(),
            )

            view?.enableSwitchControls(
                state.get().currentFiles.size > 1,
                state.get().currentFiles.isNotEmpty(),
            )

            if (playbackState.maxTimeInMilliseconds > 0) {
                val currentTime = playbackState.currentTimeInMilliseconds
                val maxTime = playbackState.maxTimeInMilliseconds
                view?.setProgressAvailable()
                view?.setProgress(currentTime, maxTime)
            } else {
                view?.setProgressNotAvailable()
            }
        } else {
            view?.enablePlayControls(false, false, false)
            view?.enableSwitchControls(false, false)
            view?.setProgressNotAvailable()
        }
    }

    private val listener: PlaybackModel.Listener = object : PlaybackModel.Listener {
        override fun onPlaybackUpdate(state: PlaybackState) {
            updateView()
        }

        override fun onPlaybackError(error: Throwable) {
        }
    }
}
