package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;

import java.util.ArrayList;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Created by y.zozulia on 09.12.2015.
 */
public class ToolbarPopupAdapter extends ArrayAdapter<ToolbarItem> {
	private ResourceBundle resourceBundle = new ResourceBundle();

	public ToolbarPopupAdapter(Context context) {
		super(context, 0, new ArrayList<ToolbarItem>());
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	@NonNull
	@Override
	public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = new ToolbarPopupItemView(getContext());
		}
		((ToolbarPopupItemView) convertView).setToolbarItem(getItem(position));
		convertView.setBackgroundResource(getSelectorResId(position));
		((ToolbarPopupItemView) convertView).setTextColor(this.resourceBundle.getFontColor());
		return convertView;
	}

	private int getSelectorResId(int position) {
		@DrawableRes int selectorResId;
		if (position == 0 && position == getCount() - 1) {
			selectorResId = this.resourceBundle.getTopBottomItemSelector();
		} else if (position == 0) {
			selectorResId = this.resourceBundle.getTopItemSelector();
		} else if (position > 0 && position < getCount() - 1) {
			selectorResId = this.resourceBundle.getItemSelector();
		} else if (position == getCount() - 1) {
			selectorResId = this.resourceBundle.getBottomItemSelector();
		} else {
			selectorResId = this.resourceBundle.getItemSelector();
		}
		return selectorResId;
	}
}
