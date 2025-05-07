package com.ionos.player.model

import java.io.Serializable

data class PlayerFileInfo(
	val id: String,
	val contentLength: Long,
    val additionalData: Serializable?,
): Serializable