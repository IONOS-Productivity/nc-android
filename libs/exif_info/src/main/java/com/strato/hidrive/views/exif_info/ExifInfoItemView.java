package com.strato.hidrive.views.exif_info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sergey Shandyuk on 4/4/2016.
 */
public class ExifInfoItemView extends LinearLayout {
	private TextView titleTextView;
	private ImageView iconImageView;

	public ExifInfoItemView(Context context, int icon, String text) {
		this(context, null);
		this.titleTextView.setText(text);
		this.iconImageView.setImageResource(icon);
	}

	public ExifInfoItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.exif_info_item, this, true);
		findViews();
	}

	private void findViews() {
		this.iconImageView = findViewById(R.id.icon);
		this.titleTextView = findViewById(R.id.title);
	}
}
