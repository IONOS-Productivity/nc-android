package com.strato.hidrive.domain.filemanager.sorting.comparators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by Dima Muravyov on 13.08.2016.
 */
public class GalleryInfoFileFolderComparatorTest {

	@Test
	public void testCompareGalleryInfoWithEqualTypes() {
		RemoteFileInfo galleryInfo1 = createMockGalleryInfo(true);
		RemoteFileInfo galleryInfo2 = createMockGalleryInfo(true);

		assertEquals(0, createComparator().compare(galleryInfo1, galleryInfo2));
		assertEquals(0, createComparator().compare(galleryInfo2, galleryInfo1));
	}

	@Test
	public void testCompareGalleryInfoWithDifferentTypes() {
		RemoteFileInfo greaterGalleryInfo = createMockGalleryInfo(true);
		RemoteFileInfo lessGalleryInfo = createMockGalleryInfo(false);

		assertTrue(createComparator().compare(greaterGalleryInfo, lessGalleryInfo) < 0);
		assertTrue(createComparator().compare(lessGalleryInfo, greaterGalleryInfo) > 0);
	}

	private Comparator<RemoteFileInfo> createComparator() {
		return new GalleryInfoFileFolderComparator();
	}

	private RemoteFileInfo createMockGalleryInfo(boolean isDirectory) {
		return new RemoteFileInfo("", "", "", isDirectory);
	}
}
