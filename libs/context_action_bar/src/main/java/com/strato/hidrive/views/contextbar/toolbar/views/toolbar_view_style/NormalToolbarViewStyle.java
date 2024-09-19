package com.strato.hidrive.views.contextbar.toolbar.views.toolbar_view_style;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by zuzik on 10.12.2015.
 */
public class NormalToolbarViewStyle implements ToolbarItemViewStyle {

	@Override
	public void applyToolbarItemStyle(Context context, View toolbarItemView) {
		toolbarItemView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
	}
}
