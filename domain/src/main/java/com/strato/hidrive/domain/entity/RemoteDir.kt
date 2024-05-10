package com.strato.hidrive.domain.entity

data class RemoteDir(
	val info: RemoteFileInfo,
	val children: List<RemoteFileInfo>,
)
