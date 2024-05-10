package com.strato.hidrive.common_ui.view.contextbar.strategy.popup_header;

import java.util.Objects;

public final class PopupHeaderBundle {
	public final String popupTitle;
	public final String popupDescription;

	public PopupHeaderBundle(String popupTitle, String popupDescription) {
		this.popupTitle = popupTitle;
		this.popupDescription = popupDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PopupHeaderBundle)) return false;
		PopupHeaderBundle that = (PopupHeaderBundle) o;
		return Objects.equals(this.popupTitle, that.popupTitle) &&
				Objects.equals(this.popupDescription, that.popupDescription);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.popupTitle, this.popupDescription);
	}
}