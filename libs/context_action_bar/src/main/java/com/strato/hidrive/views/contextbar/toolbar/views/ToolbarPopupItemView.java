package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strato.hidrive.views.contextbar.R;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;


/**
 * Created by zuzik on 09.12.2015.
 */
public class ToolbarPopupItemView extends LinearLayout {

	private final TextView tvTitle;

	public ToolbarPopupItemView(Context context) {
		this(context, null);
	}

	public ToolbarPopupItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.view_toolbar_popup_item, this);
		this.tvTitle = findViewById(R.id.stylizedButton);
	}

	public void setToolbarItem(ToolbarItem item) {
		String title = item.getTitleResId() != 0 ? getContext().getString(item.getTitleResId()) : "";
		this.tvTitle.setText(title);
	}

	public void setTextColor(int color) {
		this.tvTitle.setTextColor(getResources().getColor(color));
	}
}
