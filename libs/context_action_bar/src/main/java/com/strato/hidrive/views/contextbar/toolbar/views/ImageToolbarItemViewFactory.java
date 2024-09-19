package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;


/**
 * Created by y.zozulia on 09.12.2015.
 */
public class ImageToolbarItemViewFactory implements SingleTypeToolbarItemViewFactory {

	public ToolbarItemView create(Context context, ToolbarItem item) {
		return new ImageToolbarItemView(context, item);
	}
}
