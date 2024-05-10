package com.strato.hidrive.domain.manager.merger

import com.strato.hidrive.domain.entity.RemoteFileInfo
import com.strato.hidrive.domain.entity.RemoteFileInfoWithCollatedName
import com.strato.hidrive.domain.utils.unicode_text.CollatorWrapper
import com.strato.hidrive.domain.utils.unicode_text.CollatorWrapperFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class FileInfoCacheMergerTest {

	private val collatorFactory = CollatorWrapperFactory {
		object : CollatorWrapper {
			override fun getCollationKeyByteArray(source: String): ByteArray {
				return source.toByteArray()
			}

			override fun compare(o1: String, o2: String): Int {
				return o1.compareTo(o2)
			}
		}
	}

	private val collator = collatorFactory.create()
	private val merger = RemoteFileInfoCacheMerger(collatorFactory)

	@Test
	fun testThatNewItemsAdded() {
		val cachedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("2"),
				RemoteFileInfo("3")
			)
				.toCachedFileInfoList()

		val loadedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("2"),
				RemoteFileInfo("3"),
				RemoteFileInfo("4"),
				RemoteFileInfo("5")
			)

		val mergeResult = merger.merge(cachedChildren, loadedChildren)
		val expectedResult = listOf(
			RemoteFileInfoCacheMergerItem.Insert(RemoteFileInfo("4")),
			RemoteFileInfoCacheMergerItem.Insert(RemoteFileInfo("5"))
		)

		assertEquals(expectedResult, mergeResult)
	}

	@Test
	fun testThatOldItemsRemoved() {
		val cachedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("2"),
				RemoteFileInfo("3"),
				RemoteFileInfo("4"),
				RemoteFileInfo("5")
			)
				.toCachedFileInfoList()

		val loadedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("3"),
				RemoteFileInfo("5")
			)

		val mergeResult = merger.merge(cachedChildren, loadedChildren)
		val expectedResult = listOf(
			RemoteFileInfoCacheMergerItem.Delete(RemoteFileInfo("2")),
			RemoteFileInfoCacheMergerItem.Delete(RemoteFileInfo("4"))
		)

		assertEquals(expectedResult, mergeResult)
	}

	@Test
	fun testThatItemsWhereNotRearranged() {
		val cachedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("2"),
				RemoteFileInfo("3"),
				RemoteFileInfo("4"),
				RemoteFileInfo("5")
			)
				.toCachedFileInfoList()

		val loadedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("3"),
				RemoteFileInfo("4"),
				RemoteFileInfo("5")
			)

		val mergeResult = merger.merge(cachedChildren, loadedChildren)
		val expectedResult = listOf(
			RemoteFileInfoCacheMergerItem.Delete(
				RemoteFileInfo(
					"2"
				)
			)
		)

		assertEquals(expectedResult, mergeResult)
	}

	@Test
	fun testThatItemsRearranged() {
		val cachedChildren =
			listOf(
				RemoteFileInfo("1"),
				RemoteFileInfo("2"),
				RemoteFileInfo("3")
			)
				.toCachedFileInfoList()

		val loadedChildren =
			listOf(
				RemoteFileInfo("3"),
				RemoteFileInfo("1"),
				RemoteFileInfo("2")
			)

		val mergeResult = merger.merge(cachedChildren, loadedChildren)
		val expectedResult = listOf(
			RemoteFileInfoCacheMergerItem.Update(
				RemoteFileInfo("3"),
				RemoteFileInfo("1")
			),
			RemoteFileInfoCacheMergerItem.Update(
				RemoteFileInfo("1"),
				RemoteFileInfo("2")
			),
			RemoteFileInfoCacheMergerItem.Update(
				RemoteFileInfo("2"),
				RemoteFileInfo("3")
			)
		)

		assertEquals(expectedResult, mergeResult)
	}

	@Test
	fun testThatItemsUpdatedCorrectly() {
		val cachedChildren =
			listOf(
				createRemoteFileInfo("1", 12),
				createRemoteFileInfo("2", 12),
				RemoteFileInfo("3")
			)
				.toCachedFileInfoList()

		val loadedChildren =
			listOf(
				createRemoteFileInfo("1", 24),
				createRemoteFileInfo("2", 12),
				RemoteFileInfo("4")
			)

		val mergeResult = merger.merge(cachedChildren, loadedChildren)
		val expectedResult = listOf(
			RemoteFileInfoCacheMergerItem.Update(
				createRemoteFileInfo("1", 24),
				createRemoteFileInfo("1", 12)
			),
			RemoteFileInfoCacheMergerItem.Insert(RemoteFileInfo("4")),
			RemoteFileInfoCacheMergerItem.Delete(RemoteFileInfo("3"))
		)

		assertEquals(expectedResult, mergeResult)
	}

	@Test
	fun testThatItemsWithExtraPropertiesUpdatedCorrectly() {
		val fileInfo = createRemoteFileInfo("2", 12)
		val cachedChildren =
			listOf(
				RemoteFileInfoWithCollatedName(
					createRemoteFileInfo("1", 12),
					byteArrayOf(0, 1, 22, 44)
				),
				RemoteFileInfoWithCollatedName(
					fileInfo,
					collator.getCollationKeyByteArray(fileInfo.name)
				),
				RemoteFileInfoWithCollatedName(
					createRemoteFileInfo("3", 12),
					byteArrayOf(0, 1, 12, 44)
				),
				RemoteFileInfoWithCollatedName(
					createRemoteFileInfo("5", 12),
					byteArrayOf(0, 16, 22, 44)
				),
			)

		val loadedChildren =
			listOf(
				createRemoteFileInfo("1", 24),
				fileInfo,
				RemoteFileInfo("4"),
				createRemoteFileInfo("5", 12),
			)

		val mergeResult = merger.merge(cachedChildren, loadedChildren)

		val expectedResult = listOf(
			RemoteFileInfoCacheMergerItem.Update(
				createRemoteFileInfo("1", 24),
				createRemoteFileInfo("1", 12)
			),
			RemoteFileInfoCacheMergerItem.Insert(RemoteFileInfo("4")),
			RemoteFileInfoCacheMergerItem.Update(
				createRemoteFileInfo("5", 12),
				createRemoteFileInfo("5", 12)
			),
			RemoteFileInfoCacheMergerItem.Delete(createRemoteFileInfo("3", 12)),
		)

		assertEquals(expectedResult, mergeResult)
	}

	private fun createRemoteFileInfo(path: String, contentLength: Long): RemoteFileInfo =
		RemoteFileInfo(
			path = path,
			contentLength = contentLength
		)

	private fun List<RemoteFileInfo>.toCachedFileInfoList() =
		this.map { RemoteFileInfoWithCollatedName(it, collator.getCollationKeyByteArray(it.name)) }
}