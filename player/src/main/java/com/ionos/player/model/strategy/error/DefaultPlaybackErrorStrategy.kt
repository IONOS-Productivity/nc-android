package com.ionos.player.model.strategy.error

import com.ionos.player.model.state.PlaybackState
import javax.inject.Inject

class DefaultPlaybackErrorStrategy @Inject constructor() : PlaybackErrorStrategy {

    override fun switchToNextSource(throwable: Throwable, playbackState: PlaybackState): Boolean {
        val currentFiles = playbackState.currentFiles
        val oneFileInQueue = currentFiles.size == 1
        val endOfQueue = playbackState.currentItemState
            .map { currentFiles.indexOf(it.file) == currentFiles.lastIndex }
            .orElse(false)
        return !oneFileInQueue && !endOfQueue
    }
}
