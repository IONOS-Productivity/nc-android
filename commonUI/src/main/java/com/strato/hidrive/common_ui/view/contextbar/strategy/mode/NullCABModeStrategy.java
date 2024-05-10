package com.strato.hidrive.common_ui.view.contextbar.strategy.mode;

/**
 * Created by yaz on 7/11/16.
 */
public class NullCABModeStrategy implements ICABModeStrategy {

	private static final NullCABModeStrategy INSTANCE = new NullCABModeStrategy();

	public static NullCABModeStrategy getInstance() {
		return INSTANCE;
	}

	private NullCABModeStrategy() {
	}

	@Override
	public int getRootLayoutBackgroundColor() {
		return 0;
	}

	@Override
	public boolean isLeftAligned() {
		return false;
	}
}
