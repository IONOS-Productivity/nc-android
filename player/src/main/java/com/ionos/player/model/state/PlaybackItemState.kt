package com.ionos.player.model.state

import com.ionos.player.model.PlaybackFile
import java.io.Serializable
import java.util.Optional

data class PlaybackItemState(
    @JvmField val file: PlaybackFile,
    @JvmField val playerState: PlayerState,
    @JvmField val videoSize: Optional<VideoSize>,
    @JvmField val currentTimeInMilliseconds: Int,
    @JvmField val maxTimeInMilliseconds: Int,
) : Serializable
