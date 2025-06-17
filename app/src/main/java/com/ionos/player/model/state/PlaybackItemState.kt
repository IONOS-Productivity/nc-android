package com.ionos.player.model.state

import com.ionos.player.model.PlaybackFile
import java.io.Serializable

data class PlaybackItemState(
    @JvmField val file: PlaybackFile,
    @JvmField val playerState: PlayerState,
    @JvmField val metadata: PlaybackItemMetadata?,
    @JvmField val videoSize: VideoSize?,
    @JvmField val currentTimeInMilliseconds: Int,
    @JvmField val maxTimeInMilliseconds: Int,
) : Serializable
