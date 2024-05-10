package com.strato.hidrive.domain.manager.merger

import com.strato.hidrive.domain.entity.RemoteFileInfo

sealed class RemoteFileInfoCacheMergerItem(val entity: RemoteFileInfo) {
	class Update(entity: RemoteFileInfo, cachedEntity: RemoteFileInfo) : RemoteFileInfoCacheMergerItem(entity) {
		val isContentUpdated = entity.contentLength != cachedEntity.contentLength
	}
	class Insert(entity: RemoteFileInfo) : RemoteFileInfoCacheMergerItem(entity)
	class Delete(entity: RemoteFileInfo) : RemoteFileInfoCacheMergerItem(entity)

	override fun equals(other: Any?): Boolean = this === other ||
			(other is RemoteFileInfoCacheMergerItem && javaClass == other.javaClass && entity == other.entity)

	override fun hashCode() = entity.hashCode()
}