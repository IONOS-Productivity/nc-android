package com.viseven.develop.multipleplayermvp.null_object;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerHidingView<SourceInfo> implements MultiplePlayer.HidingView<SourceInfo> {

	private static final NullMultiplePlayerHidingView INSTANCE = new NullMultiplePlayerHidingView();

	public static <SourceInfo> NullMultiplePlayerHidingView<SourceInfo> getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerHidingView() {
	}

	@Override
	public void displayPlayerView() {

	}

	@Override
	public void doNotDisplayPlayerView() {

	}
}
