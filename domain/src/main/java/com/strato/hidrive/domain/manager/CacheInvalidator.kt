package com.strato.hidrive.domain.manager

import com.strato.hidrive.domain.manager.entity_cache_cleaner.CompositeCacheCleaner
import com.strato.hidrive.domain.manager.merger.RemoteFileInfoCacheMergerItem
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CacheInvalidator @Inject constructor(
	private val fileInfoCacheCleaner: CompositeCacheCleaner,
) {

	fun invalidateCache(mergerResult: List<RemoteFileInfoCacheMergerItem>)
			: Completable =
		Observable.fromIterable(mergerResult)
			.filter { item ->
				!item.entity.isDirectory
						&& (item is RemoteFileInfoCacheMergerItem.Delete
						|| (item is RemoteFileInfoCacheMergerItem.Update && item.isContentUpdated))
			}
			.doOnNext { fileInfoCacheCleaner.clean(it.entity) }
			.ignoreElements()

}