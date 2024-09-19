package com.strato.hidrive.player;

import com.annimon.stream.Optional;
import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.strato.hidrive.player.player_mode.PlayerMode;
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;
import com.viseven.develop.player.interfaces.PlaybackState;
import com.viseven.develop.player.interfaces.State;

import org.junit.Test;

import java.util.ArrayList;
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
		MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> state = createState(Optional.of(mockWithName("a")), mockWithName("a"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertFalse(switchToNext);
	}

	@Test
	public void switchToNextSourceReturnFalseIfNotOneFileQueueAndCurentFileIsLast() throws Exception {
		MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> state = createState(Optional.of(mockWithName("b")), mockWithName("a"), mockWithName("b"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertFalse(switchToNext);
	}

	@Test
	public void switchToNextSourceReturnTrueIfNotOneFileQueueAndCurrentFileIsNotLast() throws Exception {
		MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> state = createState(Optional.of(mockWithName("b")), mockWithName("a"), mockWithName("a"));

		boolean switchToNext = strategy.switchToNextSource(new RuntimeException(), state);

		assertTrue(switchToNext);
	}

	private MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> createState(Optional<PlayerFileInfo> currentFile, PlayerFileInfo... files) {
		Optional<PlaybackState<PlayerFileInfo>> current = currentFile
				.map(input -> new PlaybackState<>(State.NONE, 0, Optional.empty(), false, input, Optional.empty()));
		return new MultiplePlaybackState<>(
				Arrays.asList(files),
				new ArrayList<>(),
				current,
				false,
				false,
				PlayerMode.Mode.REGULAR);
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