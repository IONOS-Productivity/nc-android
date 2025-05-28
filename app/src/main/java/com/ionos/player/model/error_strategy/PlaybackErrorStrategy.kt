package com.ionos.player.model.error_strategy

import com.ionos.player.model.state.PlaybackState
import java.io.Serializable

interface PlaybackErrorStrategy : Serializable {
    fun switchToNextSource(error: Throwable, state: PlaybackState): Boolean
}
