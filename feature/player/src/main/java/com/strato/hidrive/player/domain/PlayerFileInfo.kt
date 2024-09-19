package com.strato.hidrive.player.domain

import java.io.Serializable

data class PlayerFileInfo(
	val id: String,
	val contentLength: Long,
    val additionalData: Serializable?,
): Serializable