/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

import android.view.SurfaceView
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.RepeatMode
import kotlinx.coroutines.flow.Flow
import java.util.Optional

interface PlaybackModel {

    val state: Optional<PlaybackState>

    suspend fun start()

    fun setFilesFlow(filesFlow: Flow<PlaybackFiles>)

    fun setFiles(files: PlaybackFiles)

    fun release()

    fun setVideoSurfaceView(surfaceView: SurfaceView?)

    fun addListener(listener: Listener)

    fun removeListener(listener: Listener)

    fun play()

    fun pause()

    fun stop()

    fun playNext()

    fun playPrevious()

    fun seekToPosition(positionInMilliseconds: Int)

    fun setRepeatMode(repeatMode: RepeatMode)

    fun setShuffle(shuffle: Boolean)

    fun switchToFile(file: PlaybackFile)

    interface Listener {

        fun onPlaybackUpdate(state: PlaybackState)

        fun onPlaybackError(error: Throwable)
    }
}
