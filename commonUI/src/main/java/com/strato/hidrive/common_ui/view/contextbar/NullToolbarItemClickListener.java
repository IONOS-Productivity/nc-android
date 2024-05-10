package com.strato.hidrive.common_ui.view.contextbar;

import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItem;

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
