package com.strato.hidrive.common_ui.dialog.stylized;

import com.strato.hidrive.common_ui.dialog.stylized.localized.LocalizedTextStrategy;
import com.strato.hidrive.common_ui.dialog.stylized.localized.StringLocalizedStrategy;
import com.strato.hidrive.common_ui.dialog.stylized.listeners.NullOnStylizedDialogButtonClickListener;
import com.strato.hidrive.common_ui.dialog.stylized.listeners.OnStylizedDialogButtonClickListener;

/**
 * Created by Sergey Shandyuk on 1/19/2017.
 */

class StylizedDialogButtonInfo {
	final LocalizedTextStrategy title;
	final boolean enabled;
	final OnStylizedDialogButtonClickListener clickListener;

	public StylizedDialogButtonInfo() {
		this(new StringLocalizedStrategy(""), true, NullOnStylizedDialogButtonClickListener.INSTANCE);
	}

	public StylizedDialogButtonInfo(LocalizedTextStrategy title, boolean enabled, OnStylizedDialogButtonClickListener clickListener) {
		this.title = title;
		this.enabled = enabled;
		this.clickListener = clickListener;
	}
}
