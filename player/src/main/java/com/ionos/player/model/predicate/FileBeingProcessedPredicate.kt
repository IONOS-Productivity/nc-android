package com.ionos.player.model.predicate

import com.ionos.player.model.PlaybackFile

interface FileBeingProcessedPredicate {
    fun satisfied(file: PlaybackFile): Boolean
}