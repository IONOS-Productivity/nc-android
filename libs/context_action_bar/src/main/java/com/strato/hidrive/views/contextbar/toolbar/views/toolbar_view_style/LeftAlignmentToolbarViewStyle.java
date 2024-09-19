package com.strato.hidrive.views.contextbar.toolbar.views.toolbar_view_style;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.strato.hidrive.views.contextbar.R;


/**
 * Created by zuzik on 10.12.2015.
 */
public class LeftAlignmentToolbarViewStyle implements ToolbarItemViewStyle {

	@Override
	public void applyToolbarItemStyle(Context context, View toolbarItemView) {
		final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		int margin = context.getResources().getDimensionPixelSize(R.dimen.contextbar_actionbar_buttons_padding);
		layoutParams.setMargins(margin, 0, margin, 0);
		toolbarItemView.setLayoutParams(layoutParams);
	}
}
