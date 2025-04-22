package com.viseven.develop.multipleplayermvp.null_object;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * Created by Anton Shevchuk on 18.01.2017.
 */

public class NullMultiplePlayerErrorView<SourceInfo> implements  MultiplePlayer.ErrorView<SourceInfo> {

	private static final NullMultiplePlayerErrorView INSTANCE = new NullMultiplePlayerErrorView();

	public static <SourceInfo> NullMultiplePlayerErrorView <SourceInfo>getInstance() {
			return INSTANCE;
	}

	private NullMultiplePlayerErrorView() {
	}


	@Override
	public void showError(String message) {

	}
}
