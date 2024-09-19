package com.strato.hidrive.player.views;

/**
 * Created by Anton Shevchuk on 15.04.2016.
 */

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.strato.hidrive.player.R;
import com.strato.hidrive.views.contextbar.toolbar.views.toolbar_view_style.ToolbarItemViewStyle;


public class AudioPlayerToolbarItemStyle implements ToolbarItemViewStyle {

	@Override
	public void applyToolbarItemStyle(Context context, View toolbarItemView) {
		int itemWidth = context.getResources().getDimensionPixelSize(R.dimen.player_audio_player_context_action_bar_item_width);
		final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemWidth,
				LinearLayout.LayoutParams.MATCH_PARENT);
		int itemLeftRightMargin = context.getResources().getDimensionPixelSize(
				R.dimen.player_audio_player_context_action_bar_item_left_right_margin);
		layoutParams.setMargins(itemLeftRightMargin, 0, itemLeftRightMargin, 0);
		toolbarItemView.setLayoutParams(layoutParams);
	}

}
