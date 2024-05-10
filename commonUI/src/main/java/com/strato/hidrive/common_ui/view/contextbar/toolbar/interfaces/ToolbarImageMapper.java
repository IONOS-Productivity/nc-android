package com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces;

import androidx.annotation.DrawableRes;

import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItemType;


/**
 * Created by zuzik on 10.12.2015.
 */
public interface ToolbarImageMapper {

	@DrawableRes
	int findImageResId(ToolbarItemType type);
}
