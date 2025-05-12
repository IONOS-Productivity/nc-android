package com.ionos.player.model

import com.ionos.player.model.state.RepeatMode
import javax.inject.Inject

class PlaybackSettings @Inject constructor() {

    val repeatMode: RepeatMode
        get() = RepeatMode.ALL

    val isShuffle: Boolean
        get() = false

    fun setRepeatMode(repeatMode: RepeatMode) {
    }

    fun setShuffle(shuffle: Boolean) {
    }
}
