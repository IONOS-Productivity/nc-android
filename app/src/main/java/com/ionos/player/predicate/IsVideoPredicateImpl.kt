package com.ionos.player.predicate

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.predicate.IsVideoPredicate
import javax.inject.Inject

class IsVideoPredicateImpl @Inject constructor(): IsVideoPredicate {
    override fun satisfied(value: PlayerFileInfo): Boolean {
        TODO("Not yet implemented")
    }
}