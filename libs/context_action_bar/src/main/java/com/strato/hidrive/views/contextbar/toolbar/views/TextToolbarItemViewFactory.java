package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;

/**
 * Created by: Alex Kucherenko
 * Date: 07.04.2017.
 */

public class TextToolbarItemViewFactory implements SingleTypeToolbarItemViewFactory {
	@Override
	public ToolbarItemView create(Context context, ToolbarItem item) {
		return new TextToolbarItemView(context, item);
	}
}
