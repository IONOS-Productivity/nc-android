package com.ionos.player.model.state

import com.ionos.player.model.PlayerFileInfo
import java.io.Serializable
import java.util.Optional

data class PlaybackItemState(
    @JvmField val sourceInfo: PlayerFileInfo,
    @JvmField val playerState: PlayerState,
    @JvmField val videoSize: Optional<VideoSize>,
    @JvmField val currentTimeInMilliseconds: Int,
    @JvmField val maxTimeInMilliseconds: Int,
) : Serializable
