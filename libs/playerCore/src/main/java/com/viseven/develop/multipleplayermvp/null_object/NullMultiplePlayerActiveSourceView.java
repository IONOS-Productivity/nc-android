package com.viseven.develop.multipleplayermvp.null_object;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerActiveSourceView<SourceInfo> implements MultiplePlayer.ActiveSourceView<SourceInfo> {

	private static final NullMultiplePlayerActiveSourceView INSTANCE = new NullMultiplePlayerActiveSourceView();

	public static <SourceInfo> NullMultiplePlayerActiveSourceView<SourceInfo> getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerActiveSourceView() {
	}

	@Override
	public void displayAsActiveSource() {

	}

	@Override
	public void displayAsInactiveSource() {

	}

	@Override
	public void setProgress(int currentTimeInMilliseconds, int totalTimeInMilliseconds) {

	}
}
