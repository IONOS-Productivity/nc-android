package com.ionos.player.model.predicate

import com.ionos.player.model.PlaybackFile
import javax.inject.Inject

class FileBeingProcessedPredicateImpl @Inject constructor(): FileBeingProcessedPredicate {
    override fun satisfied(file: PlaybackFile): Boolean {
        return false
    }
}