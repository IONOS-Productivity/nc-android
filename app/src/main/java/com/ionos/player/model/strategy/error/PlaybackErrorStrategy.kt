package com.ionos.player.model.strategy.error

import com.ionos.player.model.state.PlaybackState
import java.io.Serializable

interface PlaybackErrorStrategy : Serializable {
    fun switchToNextSource(error: Throwable, state: PlaybackState): Boolean
}
