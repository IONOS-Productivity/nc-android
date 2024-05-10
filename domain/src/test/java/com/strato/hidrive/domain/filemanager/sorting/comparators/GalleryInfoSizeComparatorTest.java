package com.strato.hidrive.domain.filemanager.sorting.comparators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by Dima Muravyov on 13.08.2016.
 */
public class GalleryInfoSizeComparatorTest {

	@Test
	public void testCompareGalleryInfoWithDifferentSizes() {
		RemoteFileInfo greaterGalleryInfo = createMockGalleryInfo(2);
		RemoteFileInfo lessGalleryInfo = createMockGalleryInfo(1);

		assertTrue(createComparator().compare(greaterGalleryInfo, lessGalleryInfo) > 0);
		assertTrue(createComparator().compare(lessGalleryInfo, greaterGalleryInfo) < 0);
	}

	@Test
	public void testCompareGalleryInfoWithEqualsSizes() {
		RemoteFileInfo galleryInfo1 = createMockGalleryInfo(1);
		RemoteFileInfo galleryInfo2 = createMockGalleryInfo(1);

		assertEquals(0, createComparator().compare(galleryInfo1, galleryInfo2));
		assertEquals(0, createComparator().compare(galleryInfo2, galleryInfo1));
	}

	private Comparator<RemoteFileInfo> createComparator() {
		return new GalleryInfoSizeComparator();
	}

	private RemoteFileInfo createMockGalleryInfo(long contentLength) {
		return new RemoteFileInfo("", "", "", false ,0, 0, contentLength);
	}
}