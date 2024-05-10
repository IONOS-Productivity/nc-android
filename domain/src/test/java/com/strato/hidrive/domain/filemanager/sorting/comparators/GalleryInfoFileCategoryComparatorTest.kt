package com.strato.hidrive.domain.filemanager.sorting.comparators

import com.strato.hidrive.domain.entity.RemoteFileInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * User: Dima Muravyov
 * Date: 23.05.2018
 */
class GalleryInfoFileCategoryComparatorTest {

	@Test
	fun testCompareFileWithDirectory() {
		val galleryInfo1 = createMockGalleryInfo("directory.aaa", true)
		val galleryInfo2 = createMockGalleryInfo("other.aaa")

		assertTrue(createComparator().compare(galleryInfo1, galleryInfo2) < 0)
		assertTrue(createComparator().compare(galleryInfo2, galleryInfo1) > 0)
	}

	@Test
	fun testComparePictureWithVideo() {
		val galleryInfo1 = createMockGalleryInfo("name.avi")
		val galleryInfo2 = createMockGalleryInfo("name.jpg")

		assertTrue(createComparator().compare(galleryInfo1, galleryInfo2) > 0)
		assertTrue(createComparator().compare(galleryInfo2, galleryInfo1) < 0)
	}

	@Test
	fun testCompareVideoWithDocument() {
		val galleryInfo1 = createMockGalleryInfo("name.mkv")
		val galleryInfo2 = createMockGalleryInfo("name.pdf")

		assertTrue(createComparator().compare(galleryInfo1, galleryInfo2) < 0)
		assertTrue(createComparator().compare(galleryInfo2, galleryInfo1) > 0)
	}

	@Test
	fun testCompareDirectoryWithFileWithoutExtension() {
		val galleryInfo1 = createMockGalleryInfo("name")
		val galleryInfo2 = createMockGalleryInfo("name", true)

		assertTrue(createComparator().compare(galleryInfo2, galleryInfo1) < 0)
		assertTrue(createComparator().compare(galleryInfo1, galleryInfo2) > 0)
	}

	@Test
	fun testCompareFilesWithSameNamesAndExtenionsInDifferentCases() {
		val galleryInfo1 = createMockGalleryInfo("name.wav")
		val galleryInfo2 = createMockGalleryInfo("name.WaV")

		assertEquals(0, createComparator().compare(galleryInfo1, galleryInfo2))
		assertEquals(0, createComparator().compare(galleryInfo2, galleryInfo1))
	}

	private fun createComparator(): Comparator<RemoteFileInfo> = GalleryInfoFileCategoryComparator()

	private fun createMockGalleryInfo(name: String, isDirectory: Boolean = false): RemoteFileInfo {
		return RemoteFileInfo(
			name = name,
			isDirectory = isDirectory,
			creationTime = 0,
			contentLength = 0)
	}
}