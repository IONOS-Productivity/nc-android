package com.viseven.develop.multipleplayer.multiple_playback_error_strategy;

import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackErrorStrategy;
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;

/**
 * Created by yaz on 1/23/17.
 */

public class SwitchToNextSourceMultiplePlaybackErrorStrategy<SourceInfo, Mode> implements MultiplePlaybackErrorStrategy<SourceInfo, Mode> {
	@Override
	public boolean switchToNextSource(Throwable error, MultiplePlaybackState<SourceInfo, Mode> state) {
		return true;
	}
}
