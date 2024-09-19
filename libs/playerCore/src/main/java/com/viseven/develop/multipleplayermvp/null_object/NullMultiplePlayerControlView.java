package com.viseven.develop.multipleplayermvp.null_object;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * User: zuzik
 * Date: 8/24/16
 */
public class NullMultiplePlayerControlView<SourceInfo> implements MultiplePlayer.ControlView<SourceInfo> {

	private static final NullMultiplePlayerControlView INSTANCE = new NullMultiplePlayerControlView();

	public static <SourceInfo> NullMultiplePlayerControlView<SourceInfo> getInstance() {
		return INSTANCE;
	}

	@Override
	public void repeat() {

	}

	@Override
	public void doNotRepeat() {

	}

	@Override
	public void shuffle() {

	}

	@Override
	public void doNotShuffle() {

	}

	@Override
	public void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds) {

	}

	@Override
	public void setProgressAvailable() {

	}

	@Override
	public void setProgressNotAvailable() {

	}

	@Override
	public void enablePlayControls(boolean play, boolean pause, boolean stop) {

	}

	@Override
	public void enableSwitchControls(boolean next, boolean previous) {

	}

	@Override
	public void setCurrentVolume(double volume) {

	}
}
