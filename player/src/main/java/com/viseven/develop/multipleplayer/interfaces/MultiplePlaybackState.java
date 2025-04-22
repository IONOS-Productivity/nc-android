package com.viseven.develop.multipleplayer.interfaces;

import com.annimon.stream.Optional;
import com.viseven.develop.player.interfaces.PlaybackState;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * User: zuzik
 * Date: 6/4/16
 */
public class MultiplePlaybackState<SourceInfo, Mode> implements Serializable {
	public final List<SourceInfo> currentSourceInfos;
	public final List<SourceInfo> originalSourceInfos;

	@Nullable
	private final PlaybackState<SourceInfo> currentPlaybackState;
	public final boolean repeatSingle;
	public final boolean shuffle;
	public final Mode mode;

	public Optional<PlaybackState<SourceInfo>> getCurrentPlaybackState() {
		return Optional.ofNullable(this.currentPlaybackState);
	}

	public MultiplePlaybackState(
			List<SourceInfo> currentSourceInfos,
			List<SourceInfo> originalSourceInfos,
			Optional<PlaybackState<SourceInfo>> currentPlaybackState,
			boolean repeatSingle,
			boolean shuffle,
			Mode mode) {
		this.currentSourceInfos = currentSourceInfos;
		this.originalSourceInfos = originalSourceInfos;
		this.currentPlaybackState = currentPlaybackState.orElse(null);
		this.repeatSingle = repeatSingle;
		this.shuffle = shuffle;
		this.mode = mode;
	}

	public MultiplePlaybackStateBuilder<SourceInfo, Mode> builder() {
		return new MultiplePlaybackStateBuilder<>(this);
	}

	public MultiplePlaybackState<SourceInfo, Mode> copy() {
		return builder().build();
	}
}
