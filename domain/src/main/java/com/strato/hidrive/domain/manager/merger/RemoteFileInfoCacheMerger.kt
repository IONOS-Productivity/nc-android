package com.strato.hidrive.domain.manager.merger

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.entity.RemoteFileInfoWithCollatedName
import com.strato.hidrive.domain.manager.merger.RemoteFileInfoCacheMergerItem.Delete
import com.strato.hidrive.domain.manager.merger.RemoteFileInfoCacheMergerItem.Insert
import com.strato.hidrive.domain.manager.merger.RemoteFileInfoCacheMergerItem.Update
import com.strato.hidrive.domain.utils.unicode_text.CollatorWrapperFactory
import java.util.LinkedList
import javax.inject.Inject

/**
 * User: Dima Muravyov
 * Date: 06.02.2019
 */
class RemoteFileInfoCacheMerger @Inject constructor(
	collatorWrapperFactory: CollatorWrapperFactory,
) {

	private val collator = collatorWrapperFactory.create()


	fun merge(
		cachedItems: List<RemoteFileInfoWithCollatedName>,
		loadedItems: List<RemoteFileInfo>,
	): List<RemoteFileInfoCacheMergerItem> {
		return checkForEmptyAndCreateMergeList(
			cachedItems,
			loadedItems,
		)
	}

	private fun checkForEmptyAndCreateMergeList(
		cachedItems: List<RemoteFileInfoWithCollatedName>,
		loadedItems: List<RemoteFileInfo>,
	): List<RemoteFileInfoCacheMergerItem> {
		return when {
			cachedItems.isEmpty() && loadedItems.isEmpty() -> emptyList()
			cachedItems.isEmpty() -> loadedItems.map { Insert(it) }
			loadedItems.isEmpty() -> cachedItems.map { Delete(it.fileInfo) }
			else -> createMergeList(cachedItems, loadedItems)
		}
	}

	private fun createMergeList(
		cachedItems: List<RemoteFileInfoWithCollatedName>,
		loadedItems: List<RemoteFileInfo>,
	): List<RemoteFileInfoCacheMergerItem> {

		val loadedItemsHashes = loadedItems.map { it.hashCode() }.toSet()
		val (cachedItemsPossibleUpdate, cachedItemsToDelete) =
			cachedItems.partition { it.fileInfo.hashCode() in loadedItemsHashes }

		val cachedItemsPossibleUpdateMap =
			cachedItemsPossibleUpdate.associateBy { it.fileInfo.hashCode() }

		val mutableList = LinkedList<RemoteFileInfoCacheMergerItem>().apply {
			loadedItems.forEachIndexed { index, loadedItem ->

				val reuseIndex =
					if (index < cachedItemsPossibleUpdate.size)
						cachedItemsPossibleUpdate[index].fileInfo.path == loadedItem.path
					else false

				val cachedItem: RemoteFileInfoWithCollatedName? =
					if (reuseIndex) cachedItemsPossibleUpdate[index]
					else cachedItemsPossibleUpdateMap[loadedItem.hashCode()]

				if (cachedItem != null) {
					val mergedItem = cachedItem.fileInfo.mergeWith(loadedItem)

					if (!reuseIndex || shouldUpdateDatabaseEntity(
							mergedItem,
							cachedItem,
						)
					) {
						add(Update(mergedItem, cachedItem.fileInfo))
					}
				} else {
					add(Insert(loadedItem))
				}
			}
			addAll(cachedItemsToDelete.map { cachedItem -> Delete(cachedItem.fileInfo) })
		}
		return mutableList
	}

	private fun shouldUpdateDatabaseEntity(
		mergedEntity: RemoteFileInfo,
		cachedItem: RemoteFileInfoWithCollatedName,
	) =
		shouldUpdateDatabaseEntity(
			mergedEntity,
			cachedItem.fileInfo
		)
				|| isCollationNameWrong(cachedItem, mergedEntity)

	private fun shouldUpdateDatabaseEntity(
		mergedEntity: RemoteFileInfo,
		cachedItem: RemoteFileInfo,
	) =
		mergedEntity.hashCode() != cachedItem.hashCode()
				|| mergedEntity.parentId != cachedItem.parentId
				|| mergedEntity.isDirectory != cachedItem.isDirectory
				|| mergedEntity.getDecodedName() != cachedItem.getDecodedName()
				|| mergedEntity.isReadable != cachedItem.isReadable
				|| mergedEntity.isWritable != cachedItem.isWritable
				|| mergedEntity.isTeamfolder != cachedItem.isTeamfolder
				|| mergedEntity.contentLength != cachedItem.contentLength
				|| mergedEntity.membersCount != cachedItem.membersCount
				|| mergedEntity.creationTime != cachedItem.creationTime
				|| mergedEntity.lastModified != cachedItem.lastModified

	private fun isCollationNameWrong(
		cachedItem: RemoteFileInfoWithCollatedName,
		mergedEntity: RemoteFileInfo,
	) =
		!cachedItem.collatedName.contentEquals(
			collator.getCollationKeyByteArray(
				mergedEntity.name
			)
		)

}