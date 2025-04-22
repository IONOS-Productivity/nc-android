/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player;

import com.annimon.stream.Optional;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.player.interfaces.PlaybackState;
import com.ionos.player.player.interfaces.State;

import org.junit.Test;

import java.util.Arrays;

import androidx.annotation.NonNull;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by yaz on 1/23/17.
 */
public class HiDriveMultiplePlaybackErrorStrategyTest {

	private final HiDriveMultiplePlaybackErrorStrategy strategy = new HiDriveMultiplePlaybackErrorStrategy();

	@Test
	public void switchToNextSourceReturnFalseIfOneFileQueue() throws Exception {
		MultiplePlaybackState<PlayerFileInfo> state = createState(Optional.of(mockWithName("a")), mockWithName("a"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertFalse(switchToNext);
	}

	@Test
	public void switchToNextSourceReturnFalseIfNotOneFileQueueAndCurentFileIsLast() throws Exception {
		MultiplePlaybackState<PlayerFileInfo> state = createState(Optional.of(mockWithName("b")), mockWithName("a"), mockWithName("b"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertFalse(switchToNext);
	}

	@Test
	public void switchToNextSourceReturnTrueIfNotOneFileQueueAndCurrentFileIsNotLast() throws Exception {
		MultiplePlaybackState<PlayerFileInfo> state = createState(Optional.of(mockWithName("b")), mockWithName("a"), mockWithName("a"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertTrue(switchToNext);
	}

	private MultiplePlaybackState<PlayerFileInfo> createState(Optional<PlayerFileInfo> currentFile, PlayerFileInfo... files) {
		Optional<PlaybackState<PlayerFileInfo>> current = currentFile
				.map(input -> new PlaybackState<>(State.NONE, 0, Optional.empty(), input, Optional.empty()));
		return new MultiplePlaybackState<>(
				Arrays.asList(files),
				current,
				false,
				false);
	}

	@NonNull
	private PlayerFileInfo mockWithName(@NonNull String name){
		return new PlayerFileInfo(
				name,
				name.length(),
				name
		);
	}
}