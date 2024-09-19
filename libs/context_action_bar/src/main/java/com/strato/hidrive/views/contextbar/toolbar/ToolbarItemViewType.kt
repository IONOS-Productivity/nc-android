package com.strato.hidrive.views.contextbar.toolbar

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
