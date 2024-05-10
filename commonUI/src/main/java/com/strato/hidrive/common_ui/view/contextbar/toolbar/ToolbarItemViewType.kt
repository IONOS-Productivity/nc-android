package com.strato.hidrive.common_ui.view.contextbar.toolbar

data class ToolbarItemViewType(val id: String) {

	companion object {
		@JvmField
		val IMAGE = ToolbarItemViewType("image")

		@JvmField
		val TEXT = ToolbarItemViewType("text")

		@JvmField
		val DEFAULT = IMAGE
	}
}
