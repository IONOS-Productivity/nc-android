package com.strato.hidrive.player;

import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.strato.hidrive.player.player_mode.PlayerMode;
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackErrorStrategy;
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;
import com.viseven.develop.player.exception.AudioFocusLostException;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by yaz on 1/23/17.
 */

public class HiDriveMultiplePlaybackErrorStrategy implements MultiplePlaybackErrorStrategy<PlayerFileInfo, PlayerMode.Mode> {

	@Inject
	public HiDriveMultiplePlaybackErrorStrategy(){
	}

	@Override
	public boolean switchToNextSource(Throwable throwable, final MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> multiplePlaybackState) {
		if (throwable instanceof AudioFocusLostException) {
			return false;
		}
		final List<PlayerFileInfo> sourceInfos = multiplePlaybackState.currentSourceInfos;
		boolean oneFileInQueue = sourceInfos.size() == 1;
		boolean endOfQueue = multiplePlaybackState.getCurrentPlaybackState()
				.map(playbackState -> sourceInfos.indexOf(playbackState.sourceInfo) == sourceInfos.size() - 1)
				.orElse(false);
		return !oneFileInQueue && !endOfQueue;
	}
}
