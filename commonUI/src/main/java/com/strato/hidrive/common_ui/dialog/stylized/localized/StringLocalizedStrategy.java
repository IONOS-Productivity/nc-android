package com.strato.hidrive.common_ui.dialog.stylized.localized;

/**
 * Created by Sergey Shandyuk on 7/29/2016.
 */
public class StringLocalizedStrategy implements LocalizedTextStrategy {
	private final String text;

	public StringLocalizedStrategy(String text) {
		this.text = text;
	}

	public String getLocalizedString() {
		return this.text;
	}
}
