package com.strato.hidrive.player.views.infinite_view_pager;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: Anton Shevchuk
 * Date: 03.02.2017
 */
@Config(sdk = 24, manifest = Config.NONE)
public class InfiniteViewPagerTest {
	private final List<Integer> inputList = new ArrayList<>();

	@Before
	public void setup() {
		inputList.add(1);
		inputList.add(2);
		inputList.add(3);
	}

	@Test
	public void testRotate() {
		List<Integer> expectedList = new ArrayList<>();
		expectedList.add(3);
		expectedList.add(1);
		expectedList.add(2);
		assertTrue(listsEntriesEqual(InfiniteViewPager.rotate(inputList, 1), expectedList));
	}

	@Test
	public void testCalculateShift() {
		List<Integer> secondList = new ArrayList<>();
		secondList.add(3);
		secondList.add(1);
		secondList.add(2);
		assertEquals(2, InfiniteViewPager.calculateShift(secondList, 0, inputList.get(0)));
	}

	private boolean listsEntriesEqual(List first, List second) {
		if (first.size() != second.size()) {
			return false;
		}
		for (int i = 0; i < first.size(); i++) {
			if (!first.get(i).equals(second.get(i))) {
				return false;
			}
		}
		return true;
	}
}
