package com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces;

import androidx.annotation.StringRes;

import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItemType;


/**
 * Created by zuzik on 10.12.2015.
 */
public interface ToolbarTextMapper {

	@StringRes
	int findTextResId(ToolbarItemType type);
}
