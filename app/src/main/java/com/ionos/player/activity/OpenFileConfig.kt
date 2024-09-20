package com.ionos.player.activity

import com.owncloud.android.datamodel.OCFile
import java.io.Serializable

data class OpenFileConfig(
	val fileInfo: OCFile,
	// val sortType: SortType,
	// val sourceMode: FileSourceMode,
) : Serializable