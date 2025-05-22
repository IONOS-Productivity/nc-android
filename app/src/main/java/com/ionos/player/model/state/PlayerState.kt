package com.ionos.player.model.state

import java.io.Serializable

enum class PlayerState : Serializable {
    IDLE,
    PLAYING,
    PAUSED,
    COMPLETED,
    NONE,
}
