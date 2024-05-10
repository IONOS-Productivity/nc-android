package com.strato.hidrive.domain.filemanager.sorting.comparators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by Dima Muravyov on 13.08.2016.
 */
public class GalleryInfoTypeComparatorTest {

	@Test
	public void testCompareGalleryInfoWithEqualsExtensions() {
		RemoteFileInfo galleryInfo1 = createMockGalleryInfo("name.aaa");
		RemoteFileInfo galleryInfo2 = createMockGalleryInfo("other.aaa");

		assertEquals(0, createComparator().compare(galleryInfo1, galleryInfo2));
		assertEquals(0, createComparator().compare(galleryInfo2, galleryInfo1));
	}

	@Test
	public void testCompareGalleryInfoWithDifferentExtensions() {
		RemoteFileInfo greaterGalleryInfo = createMockGalleryInfo("name.aaa");
		RemoteFileInfo lessGalleryInfo = createMockGalleryInfo("name.bbb");

		assertTrue(createComparator().compare(greaterGalleryInfo, lessGalleryInfo) < 0);
		assertTrue(createComparator().compare(lessGalleryInfo, greaterGalleryInfo) > 0);
	}

	@Test
	public void testCompareGalleryInfoWithExtensionAndFileWithoutIt() {
		RemoteFileInfo greaterGalleryInfo = createMockGalleryInfo("name.bbb");
		RemoteFileInfo lessGalleryInfo = createMockGalleryInfo("name");

		assertTrue(createComparator().compare(greaterGalleryInfo, lessGalleryInfo) < 0);
		assertTrue(createComparator().compare(lessGalleryInfo, greaterGalleryInfo) > 0);
	}

	@Test
	public void testCompareGalleryInfoWithExtensionInDifferentCases() {
		RemoteFileInfo greaterGalleryInfo = createMockGalleryInfo("name.BBB");
		RemoteFileInfo lessGalleryInfo = createMockGalleryInfo("name.bbb");

		assertEquals(0, createComparator().compare(greaterGalleryInfo, lessGalleryInfo));
		assertEquals(0, createComparator().compare(lessGalleryInfo, greaterGalleryInfo));
	}

	@Test
	public void testCompareGalleryInfoWithoutExtension() {
		RemoteFileInfo galleryInfo1 = createMockGalleryInfo("name");
		RemoteFileInfo galleryInfo2 = createMockGalleryInfo("other");

		assertEquals(0, createComparator().compare(galleryInfo1, galleryInfo2));
		assertEquals(0, createComparator().compare(galleryInfo2, galleryInfo1));
	}

	private Comparator<RemoteFileInfo> createComparator() {
		return new GalleryInfoTypeComparator();
	}

	private RemoteFileInfo createMockGalleryInfo(String name) {
		return new RemoteFileInfo("", "", name);
	}
}
