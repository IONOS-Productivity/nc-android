package com.strato.hidrive.player.views.player.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strato.hidrive.player.R;
import com.strato.hidrive.player.di.PlayerComponent;
import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.strato.hidrive.player.transformation.FileInfoToDisplayNameTransformation;
import com.strato.hidrive.player.transformation.FileInfoToLastModifiedDateTransformation;
import com.strato.hidrive.player.transformation.FileInfoToStringSizeTransformation;

import javax.inject.Inject;

/**
 * Created by y.zozulia on 22.06.2015.
 */
public class FileTextDetailView extends LinearLayout {

	@Inject
	FileInfoToStringSizeTransformation toStringSizeTransformation;
	@Inject
	FileInfoToDisplayNameTransformation toDisplayNameTransformation;
	@Inject
	FileInfoToLastModifiedDateTransformation toLastModifiedDateTransformation;

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
		PlayerComponent.Companion.from(context).inject(this);
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
		showData(toDisplayNameTransformation.transform(file), createSubtitleText(file));
	}

	private void showData(String title, String subtitle) {
		this.tvTitle.setText(title);
		this.tvSubtitle.setText(subtitle);
	}

	private String createSubtitleText(PlayerFileInfo file) {
		StringBuilder textBuilder = new StringBuilder();

		if (file.getContentLength() != 0) {
			textBuilder.append(toStringSizeTransformation.transform(file));
		}

		String lastModifiedDateDescription = toLastModifiedDateTransformation.transform(file);
		if (!lastModifiedDateDescription.isEmpty()) {

			if (!textBuilder.toString().isEmpty()) {
				textBuilder.append(", ");
			}
			textBuilder.append(getResources().getString(R.string.player_last_change_date));
			textBuilder.append(" ");
			textBuilder.append(lastModifiedDateDescription);
		}
		return textBuilder.toString();
	}

}