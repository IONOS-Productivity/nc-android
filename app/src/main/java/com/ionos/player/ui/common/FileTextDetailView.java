package com.ionos.player.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ionos.player.model.PlaybackFile;
import com.owncloud.android.R;

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
		this(context, attrs, R.layout.player_file_text_detail_view);
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

	public void displayFile(PlaybackFile file) {
		showData(file.getNameWithoutExtension(), createSubtitleText(file));
	}

	private void showData(String title, String subtitle) {
		this.tvTitle.setText(title);
		this.tvSubtitle.setText(subtitle);
	}

	private String createSubtitleText(PlaybackFile file) {
		StringBuilder stringBuilder = new StringBuilder();
		if (file.getContentLength() > 0) {
			stringBuilder.append(Formatter.formatFileSize(getContext(), file.getContentLength()));
		}
		if (file.getLastModified() > 0) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(", ");
			}
			stringBuilder
					.append(getResources().getString(R.string.player_last_change_date))
					.append(" ")
					.append(DateFormat.getDateFormat(getContext()).format(new Date(file.getLastModified())));
		}
		return stringBuilder.toString();
	}

}