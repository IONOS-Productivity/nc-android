package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;

/**
 * Created by yaz on 8/15/16.
 */
public interface SingleTypeToolbarItemViewFactory {
	ToolbarItemView create(Context context, ToolbarItem item);
}
