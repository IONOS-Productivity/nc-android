package com.strato.hidrive.views.contextbar.toolbar.interfaces;

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemType;

import androidx.annotation.StringRes;


/**
 * Created by zuzik on 10.12.2015.
 */
public interface ToolbarTextMapper {

	@StringRes
	int findTextResId(ToolbarItemType type);
}
