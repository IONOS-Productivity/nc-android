package com.ionos.player.model

import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.RepeatMode
import java.util.Optional

interface PlaybackModel {

    val state: Optional<PlaybackState>

    fun start(onSuccess: () -> Unit, onError: (Throwable) -> Unit)

    fun setFiles(files: List<PlaybackFile>)

    fun release()

    fun videoViewSetter(success: (VideoViewSetter) -> Unit)

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

        fun onUpdate(state: PlaybackState)

        fun onError(error: Throwable)

        fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>)
    }
}
