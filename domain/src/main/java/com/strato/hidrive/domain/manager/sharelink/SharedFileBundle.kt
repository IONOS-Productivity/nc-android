package com.strato.hidrive.domain.manager.sharelink

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.entity.ShareLinkEntity

data class SharedFileBundle(
	val file: RemoteFileInfo,
	val shareLink: ShareLinkEntity,
	val linkWasCreated: Boolean,
)