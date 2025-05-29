package com.ionos.player.model

data class PlaybackFiles(
	val list: List<PlaybackFile>,
	val comparator: PlaybackFilesComparator
)
