package com.ionos.player.model.predicate

import com.ionos.player.model.PlaybackFile

interface IsVideoPredicate {
    fun satisfied(file: PlaybackFile): Boolean
}