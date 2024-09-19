package com.strato.hidrive.views.contextbar.toolbar;

import com.strato.hidrive.views.contextbar.toolbar.interfaces.ToolbarTextMapper;

/**
 * Created by Anton Shevchuk on 13.11.2017.
 */

public class NullToolbarTextMapper implements ToolbarTextMapper {

	public static final NullToolbarTextMapper INSTANCE = new NullToolbarTextMapper();

	private NullToolbarTextMapper () {

	}

	@Override
	public int findTextResId(ToolbarItemType type) {
		return 0;
	}
}
