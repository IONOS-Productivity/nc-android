package com.strato.hidrive.views.contextbar;

import com.strato.hidrive.views.contextbar.strategy.configuration.ToolbarItemClickListener;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;

/**
 * User: zuzik
 * Date: 25.03.2016.
 */
public class NullToolbarItemClickListener implements ToolbarItemClickListener {

	public static final NullToolbarItemClickListener INSTANCE = new NullToolbarItemClickListener();

	private NullToolbarItemClickListener() {
	}

	@Override
	public boolean onToolbarItemClick(ToolbarItem item) {
		return false;
	}
}
