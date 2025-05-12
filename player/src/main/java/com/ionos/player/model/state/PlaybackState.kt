package com.ionos.player.model.state

import com.ionos.player.model.PlayerFileInfo
import java.io.Serializable
import java.util.Optional

class PlaybackState(
    @JvmField val currentSourceInfos: List<PlayerFileInfo>,
    @JvmField val currentPlaybackItemState: Optional<PlaybackItemState>,
    @JvmField val repeatMode: RepeatMode,
    @JvmField val shuffle: Boolean,
) : Serializable
