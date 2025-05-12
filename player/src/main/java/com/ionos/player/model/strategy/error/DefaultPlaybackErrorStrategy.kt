package com.ionos.player.model.strategy.error

import com.ionos.player.model.state.PlaybackState
import javax.inject.Inject

class DefaultPlaybackErrorStrategy @Inject constructor() : PlaybackErrorStrategy {

    override fun switchToNextSource(throwable: Throwable, playbackState: PlaybackState): Boolean {
        val sourceInfos = playbackState.currentSourceInfos
        val oneFileInQueue = sourceInfos.size == 1
        val endOfQueue = playbackState.currentPlaybackItemState
            .map { sourceInfos.indexOf(it.sourceInfo) == sourceInfos.lastIndex }
            .orElse(false)
        return !oneFileInQueue && !endOfQueue
    }
}
