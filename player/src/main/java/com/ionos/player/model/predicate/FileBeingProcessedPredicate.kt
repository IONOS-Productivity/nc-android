package com.ionos.player.model.predicate

import com.ionos.player.model.PlayerFileInfo

interface FileBeingProcessedPredicate {
    fun satisfied(fileInfo: PlayerFileInfo): Boolean
}