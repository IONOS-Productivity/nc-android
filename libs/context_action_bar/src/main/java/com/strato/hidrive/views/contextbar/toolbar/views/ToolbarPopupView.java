package com.strato.hidrive.views.contextbar.toolbar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.strato.hidrive.views.contextbar.R;
import com.strato.hidrive.views.contextbar.strategy.popup_header.PopupHeaderBundle;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;

import java.util.List;

/**
 * Created by y.zozulia on 09.12.2015.
 */
public class ToolbarPopupView extends LinearLayout {

	public interface ToolbarPopupViewListener {

		void onItemClicked(ToolbarItem item);
	}

	private final ListView listView;

	private final ToolbarPopupAdapter adapter;

	private ToolbarPopupViewListener listener;
	private final View header;
	private final TextView title;
	private final TextView description;
	private final View separator;

	public ToolbarPopupView(Context context) {
		this(context, null);
	}

	public ToolbarPopupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.view_toolbar_popup, this);
		this.listView = findViewById(R.id.listView);
		this.title = findViewById(R.id.tvTitle);
		this.header = findViewById(R.id.llHeader);
		this.separator = findViewById(R.id.separator);
		this.description = findViewById(R.id.tvDescription);
		this.adapter = new ToolbarPopupAdapter(context);
		this.listView.setAdapter(this.adapter);
		this.listView.setOnItemClickListener((parent, view, position, id) -> {
			if (listener != null) listener.onItemClicked(adapter.getItem(position));
		});
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.adapter.setResourceBundle(resourceBundle);
		this.listView.setDivider(resourceBundle.getDividerColor());
	}

	public void setListener(ToolbarPopupViewListener listener) {
		this.listener = listener;
	}

	public void setToolbarItems(List<ToolbarItem> items) {
		this.adapter.clear();
		for (ToolbarItem item : items) {
			this.adapter.add(item);
		}
		this.adapter.notifyDataSetChanged();
	}

	public void setPopupHeaderBundleOptional(PopupHeaderBundle popupHeaderBundle) {
		if (popupHeaderBundle.popupTitle != null) {
			this.title.setText(popupHeaderBundle.popupTitle);
			this.title.setVisibility(View.VISIBLE);
			this.header.setVisibility(View.VISIBLE);
			this.separator.setVisibility(View.VISIBLE);
		}
		if (popupHeaderBundle.popupDescription != null) {
			this.description.setText(popupHeaderBundle.popupDescription);
			this.description.setVisibility(View.VISIBLE);
			this.header.setVisibility(View.VISIBLE);
			this.separator.setVisibility(View.VISIBLE);
		}
	}
}
