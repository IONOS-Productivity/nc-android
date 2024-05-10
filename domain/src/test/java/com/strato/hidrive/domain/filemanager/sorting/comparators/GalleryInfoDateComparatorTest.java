package com.strato.hidrive.domain.filemanager.sorting.comparators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.strato.hidrive.domain.entity.RemoteFileInfo;

import org.junit.Test;

import java.util.Comparator;

/**
 * Created by Dima Muravyov on 13.08.2016.
 */
public class GalleryInfoDateComparatorTest {

	@Test
	public void testCompareItemsWithDifferentModificationDate() {
		RemoteFileInfo greaterItem = createMockItem(2, 3);
		RemoteFileInfo lessItem = createMockItem(1, 4);

		assertTrue(createComparator().compare(greaterItem, lessItem) > 0);
		assertTrue(createComparator().compare(lessItem, greaterItem) < 0);
	}

	@Test
	public void testCompareItemsInfoWithEqualsModificationDate() {
		RemoteFileInfo Item1 = createMockItem(1, 3);
		RemoteFileInfo Item2 = createMockItem(1, 3);

		assertEquals(0, createComparator().compare(Item1, Item2));
		assertEquals(0, createComparator().compare(Item2, Item1));
	}

	private Comparator<RemoteFileInfo> createComparator() {
		return new GalleryInfoDateComparator();
	}

	private RemoteFileInfo createMockItem(long lastModifiedTime, long creationTime) {
		return new RemoteFileInfo(
				"", "", "", false, lastModifiedTime, creationTime, 0);
	}
}
