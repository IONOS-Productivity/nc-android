package com.strato.hidrive.common_ui.remote_target

import com.strato.hidrive.domain.entity.RemoteFileInfo
import java.io.Serializable

/**
 * Created by: Alex Kucherenko
 * Date: 29.11.2018.
 */
data class RemoteTargetOperationsParams @JvmOverloads constructor(
	val type: RemoteTargetOperationsType,
	val source: RemoteTargetOperationsSource,
	val initialPath: String,
	@Transient val fileInfos: List<RemoteFileInfo> = listOf(),
) : Serializable
