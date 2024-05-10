package com.strato.hidrive.domain.entity

import com.strato.hidrive.domain.utils.file_view_display_params.SortType
import java.io.Serializable

data class OpenFileConfig(
	val fileInfo: RemoteFileInfo,
	val sortType: SortType,
	val sourceMode: FileSourceMode,
) : Serializable