package com.strato.hidrive.common_ui.view.contextbar.toolbar;

import com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces.ToolbarImageMapper;

/**
 * Created by Anton Shevchuk on 13.11.2017.
 */

public class NullToolbarImageMapper implements ToolbarImageMapper {
	public static final NullToolbarImageMapper INSTANCE = new NullToolbarImageMapper();

	private NullToolbarImageMapper () {

	}

	@Override
	public int findImageResId(ToolbarItemType type) {
		return 0;
	}
}
