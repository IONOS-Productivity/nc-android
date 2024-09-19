package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.views.contextbar.utils.ParamAction;

import androidx.annotation.Nullable;

/**
 * Created by yaz on 8/15/16.
 */
public abstract class ToolbarItemView extends LinearLayout {

	private final ToolbarItem item;

	public abstract void setOnToolbarItemViewClickListener(@Nullable final ParamAction<ToolbarItem> action);

	protected ToolbarItemView(Context context, ToolbarItem item) {
		super(context);
		this.item = item;
	}

	public final ToolbarItem getItem() {
		return this.item;
	}
}
