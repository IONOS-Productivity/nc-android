package com.ionos.player.model.state

import java.io.Serializable

data class VideoSize(
    val width: Int,
    val height: Int,
) : Serializable
