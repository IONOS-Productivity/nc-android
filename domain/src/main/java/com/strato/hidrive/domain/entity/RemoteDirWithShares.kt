package com.strato.hidrive.domain.entity

data class RemoteDirWithShares(
	val dir: RemoteDir,
	val dirShares: List<ShareLinkEntity>,
	val childShares: List<ShareLinkEntity>,
)
