package com.ionos.player.predicate

import com.ionos.player.domain.PlayerFileInfo
import javax.inject.Inject

class FileBeingProcessedPredicateImpl @Inject constructor(): FileBeingProcessedPredicate {
    override fun satisfied(value: PlayerFileInfo): Boolean {
        return false
    }
}