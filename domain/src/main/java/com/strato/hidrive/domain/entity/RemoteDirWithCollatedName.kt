package com.strato.hidrive.domain.entity

data class RemoteDirWithCollatedName(
	val info: RemoteFileInfoWithCollatedName,
	val children: List<RemoteFileInfoWithCollatedName>,
)