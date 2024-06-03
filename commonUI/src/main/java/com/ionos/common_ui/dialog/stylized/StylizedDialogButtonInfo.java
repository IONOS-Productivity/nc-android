package com.ionos.common_ui.dialog.stylized;

import com.ionos.common_ui.dialog.stylized.listeners.NullOnStylizedDialogButtonClickListener;
import com.ionos.common_ui.dialog.stylized.listeners.OnStylizedDialogButtonClickListener;
import com.ionos.common_ui.dialog.stylized.localized.LocalizedTextStrategy;
import com.ionos.common_ui.dialog.stylized.localized.StringLocalizedStrategy;

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
