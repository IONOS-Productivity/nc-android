/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.pager;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerPagerTest {
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
		assertTrue(listsEntriesEqual(PlayerPager.rotate(inputList, 1), expectedList));
	}

	@Test
	public void testCalculateShift() {
		List<Integer> secondList = new ArrayList<>();
		secondList.add(3);
		secondList.add(1);
		secondList.add(2);
		assertEquals(2, PlayerPager.calculateShift(secondList, 0, inputList.get(0)));
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
