package com.ionos.player.media3.resumption

import com.ionos.player.model.PlaybackFileType
import com.owncloud.android.ui.fragment.SearchType

data class PlaybackResumptionConfig(
	val currentFileId: String,
	val folderId: Long,
	val fileType: PlaybackFileType,
	val searchType: SearchType?,
)
