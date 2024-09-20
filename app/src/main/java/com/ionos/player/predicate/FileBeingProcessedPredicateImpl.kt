package com.ionos.player.predicate

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.predicate.FileBeingProcessedPredicate
import javax.inject.Inject

class FileBeingProcessedPredicateImpl @Inject constructor(): FileBeingProcessedPredicate {
    override fun satisfied(value: PlayerFileInfo): Boolean {
        return false
    }
}