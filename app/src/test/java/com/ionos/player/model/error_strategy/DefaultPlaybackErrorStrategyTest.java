/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.error_strategy;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.state.PlaybackItemMetadata;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlayerState;
import com.ionos.player.model.state.RepeatMode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import androidx.annotation.NonNull;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DefaultPlaybackErrorStrategyTest {

	private final DefaultPlaybackErrorStrategy strategy = new DefaultPlaybackErrorStrategy();

	@Test
	public void switchToNextSourceReturnFalseIfOneFileQueue() throws Exception {
		PlaybackState state = createState(Optional.of(mockWithName("a")), mockWithName("a"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertFalse(switchToNext);
	}

	@Test
	public void switchToNextSourceReturnFalseIfNotOneFileQueueAndCurentFileIsLast() throws Exception {
		PlaybackState state = createState(Optional.of(mockWithName("b")), mockWithName("a"), mockWithName("b"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertFalse(switchToNext);
	}

	@Test
	public void switchToNextSourceReturnTrueIfNotOneFileQueueAndCurrentFileIsNotLast() throws Exception {
		PlaybackState state = createState(Optional.of(mockWithName("b")), mockWithName("a"), mockWithName("a"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertTrue(switchToNext);
	}

	private PlaybackState createState(Optional<PlaybackFile> currentFile, PlaybackFile... files) {
		Optional<PlaybackItemState> current = currentFile
				.map(input -> new PlaybackItemState(input, PlayerState.NONE, null, null, 0, 0));
		return new PlaybackState(
				Arrays.asList(files),
				current.orElse(null),
				RepeatMode.OFF,
				false);
	}

	@NonNull
	private PlaybackFile mockWithName(@NonNull String name){
		return new PlaybackFile(
            name,
			name,
			"fakeUri:///" + name,
			"audio/mp3",
			0,
			0,
			false
		);
	}
}