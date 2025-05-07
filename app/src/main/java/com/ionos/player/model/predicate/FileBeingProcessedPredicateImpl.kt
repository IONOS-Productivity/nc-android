package com.ionos.player.model.predicate

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class FileBeingProcessedPredicateImpl @Inject constructor(): FileBeingProcessedPredicate {
    override fun satisfied(value: PlayerFileInfo): Boolean {
        return false
    }
}