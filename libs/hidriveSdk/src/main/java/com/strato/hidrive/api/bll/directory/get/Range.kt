package com.strato.hidrive.api.bll.directory.get

data class Range(
	val offset: Int,
	val limit: Int,
) {
	companion object {
		@JvmField
		val SINGLE_ITEM = Range(0, 1)
	}
}
