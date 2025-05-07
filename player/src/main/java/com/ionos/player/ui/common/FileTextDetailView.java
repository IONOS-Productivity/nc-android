package com.ionos.player.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ionos.player.R;
import com.ionos.player.model.PlayerFileInfo;

import java.util.Date;

/**
 * Created by y.zozulia on 22.06.2015.
 */
public class FileTextDetailView extends LinearLayout {

	private TextView tvTitle;
	private TextView tvSubtitle;

	public FileTextDetailView(Context context) {
		this(context, null);
	}

	public FileTextDetailView(Context context, AttributeSet attrs) {
		this(context, attrs, R.layout.view_file_text_detail);
	}

	protected FileTextDetailView(Context context, AttributeSet attrs, int layoutResId) {
		super(context, attrs);
		if (isInEditMode()) {
			return;
		}
		LayoutInflater.from(getContext()).inflate(layoutResId, this);
		findViews();
		readAttrs(attrs);
	}

	private void findViews() {
		this.tvTitle = findViewById(R.id.tvTitle);
		this.tvSubtitle = findViewById(R.id.tvSubtitle);
	}

	private void readAttrs(AttributeSet attrs) {
		TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FileTextDetailView, 0, 0);
		try {
			this.tvTitle.setTextColor(array.getColor(R.styleable.FileTextDetailView_title_text_color, getResources().getColor(R.color.player_common_text_color)));
			this.tvSubtitle.setTextColor(array.getColor(R.styleable.FileTextDetailView_subtitle_text_color, getResources().getColor(R.color.player_common_secondary_text_color)));
			this.tvSubtitle.setVisibility(array.getBoolean(R.styleable.FileTextDetailView_subtitle_visible, true) ? VISIBLE : GONE);
		} finally {
			array.recycle();
		}
	}

	public void displayFileInfo(PlayerFileInfo file) {
		showData(file.getName(), createSubtitleText(file));
	}

	private void showData(String title, String subtitle) {
		this.tvTitle.setText(title);
		this.tvSubtitle.setText(subtitle);
	}

	private String createSubtitleText(PlayerFileInfo file) {
		return new StringBuilder()
			.append(Formatter.formatFileSize(getContext(), file.getContentLength()))
			.append(", ")
			.append(getResources().getString(R.string.player_last_change_date))
			.append(" ")
			.append(DateFormat.getDateFormat(getContext()).format(new Date(file.getLastModified())))
			.toString();
	}

}