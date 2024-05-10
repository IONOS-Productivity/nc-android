package com.strato.hidrive.domain.manager.merger

import com.strato.hidrive.domain.entity.ShareLinkEntity

/**
 * User: Dima Muravyov
 * Date: 12.02.2019
 */
sealed class ShareLinkEntityMergerItem {
	class Update(val entity: ShareLinkEntity) : ShareLinkEntityMergerItem()
	class Insert(val entity: ShareLinkEntity) : ShareLinkEntityMergerItem()
	class Delete(val entity: ShareLinkEntity) : ShareLinkEntityMergerItem()
	class None(val cachedEntity: ShareLinkEntity, val loadedEntity: ShareLinkEntity) : ShareLinkEntityMergerItem()
}