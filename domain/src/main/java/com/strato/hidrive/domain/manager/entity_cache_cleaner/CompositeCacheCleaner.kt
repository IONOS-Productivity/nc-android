package com.strato.hidrive.domain.manager.entity_cache_cleaner

import com.strato.hidrive.domain.entity.RemoteFileInfo
import javax.inject.Inject


class CompositeCacheCleaner @Inject constructor(
	private val thumbnailCacheCleaner: FileInfoThumbnailCacheCleaner,
	private val playerCacheCleaner: PlayerCacheCleaner,
) : EntityCacheCleaner<RemoteFileInfo>{

	override fun clean(entity: RemoteFileInfo) {
		thumbnailCacheCleaner.clean(entity)
		playerCacheCleaner.clean(entity)
	}

}