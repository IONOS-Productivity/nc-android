package com.ionos.player.model.predicate

import com.ionos.player.model.PlayerFileInfo

interface IsVideoPredicate {
    fun satisfied(fileInfo: PlayerFileInfo): Boolean
}