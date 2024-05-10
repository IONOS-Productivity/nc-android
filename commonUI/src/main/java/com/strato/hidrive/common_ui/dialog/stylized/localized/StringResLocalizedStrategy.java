package com.strato.hidrive.common_ui.dialog.stylized.localized;

import android.content.Context;
import android.content.ContextWrapper;
import androidx.annotation.StringRes;

/**
 * Created by Sergey Shandyuk on 7/29/2016.
 */
public class StringResLocalizedStrategy implements LocalizedTextStrategy {
	private final Context context;
	@StringRes
	private final int text;

	public StringResLocalizedStrategy(Context context, @StringRes int text) {
		this.context = new ContextWrapper(context).getApplicationContext();
		this.text = text;
	}

	public String getLocalizedString() {
		return this.context.getString(this.text);
	}
}
