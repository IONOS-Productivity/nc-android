package com.ionos.player.model.predicate

import com.ionos.player.model.PlaybackFile
import javax.inject.Inject

class FileBeingProcessedPredicate @Inject constructor() {
    fun satisfied(file: PlaybackFile): Boolean {
        return false
    }
}