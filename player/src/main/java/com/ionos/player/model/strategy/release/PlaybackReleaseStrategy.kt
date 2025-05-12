package com.ionos.player.model.strategy.release

import com.ionos.player.model.PlaybackFile
import java.io.Serializable

interface PlaybackReleaseStrategy : Serializable {
    fun releaseCurrentPlayback(files: List<PlaybackFile>, currentFile: PlaybackFile): Boolean
}
