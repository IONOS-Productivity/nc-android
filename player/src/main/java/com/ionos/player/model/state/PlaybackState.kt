package com.ionos.player.model.state

import com.ionos.player.model.PlaybackFile
import java.io.Serializable
import java.util.Optional

class PlaybackState(
    @JvmField val currentFiles: List<PlaybackFile>,
    @JvmField val currentItemState: Optional<PlaybackItemState>,
    @JvmField val repeatMode: RepeatMode,
    @JvmField val shuffle: Boolean,
) : Serializable
