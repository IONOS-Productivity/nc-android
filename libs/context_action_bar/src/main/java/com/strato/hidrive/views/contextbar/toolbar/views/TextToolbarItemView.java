package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.strato.hidrive.views.contextbar.R;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.views.contextbar.utils.ParamAction;

import androidx.annotation.Nullable;

/**
 * Created by: Alex Kucherenko
 * Date: 07.04.2017.
 */

public class TextToolbarItemView extends ToolbarItemView {

	protected TextToolbarItemView(Context context, ToolbarItem item) {
		super(context, item);
		inflate(context, R.layout.view_text_toolbar_item, this);
		TextView textView = findViewById(R.id.textView);
		textView.setText(item.getTitleResId());
	}

	@Override
	public void setOnToolbarItemViewClickListener(@Nullable final ParamAction<ToolbarItem> action) {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (action != null) {
					action.execute(getItem());
				}
			}
		});
	}
}
