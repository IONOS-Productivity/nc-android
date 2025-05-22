package com.ionos.player.model

import java.io.Serializable

data class PlaybackFile(
	val id: String,
	val uri: String,
	val name: String,
	val mimeType: String,
	val contentLength: Long,
	val lastModified: Long,
): Serializable