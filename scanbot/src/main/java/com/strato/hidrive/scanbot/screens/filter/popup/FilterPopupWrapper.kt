package com.strato.hidrive.scanbot.screens.filter.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.StringRes
import com.strato.hidrive.domain.interfaces.actions.Action
import com.strato.hidrive.scanbot.R
import com.strato.hidrive.scanbot.databinding.ScanbotFilterPopupBinding

internal class FilterPopupWrapper(context: Context, onItemClick: Action) {
	private val popup: PopupWindow
	private val popupWidth: Int
	private val popupHeight: Int
	private val binding: ScanbotFilterPopupBinding

	init {
		binding = ScanbotFilterPopupBinding.inflate(LayoutInflater.from(context))
		popupWidth = context.resources.getDimensionPixelSize(R.dimen.scanbot_filter_popup_width)
		popupHeight = context.resources.getDimensionPixelSize(R.dimen.scanbot_filter_popup_height)
		popup = PopupWindow(binding.root, popupWidth, popupHeight, true)
		configureItem(R.string.scanbot_filter_popup_apply_for_all, onItemClick)
	}

	private fun configureItem(@StringRes titleRes: Int, onItemClick: Action) {
		binding.tvPopupText.setText(titleRes)
		binding.tvPopupText.setOnClickListener {
			onItemClick.execute()
			popup.dismiss()
		}
	}

	fun showAsDropDown(anchor: View) {
		val popupHorizontalOffset: Int = -popupWidth + anchor.width
		val popupVerticalOffset: Int = -anchor.height / 2 - popupHeight / 2
		popup.showAsDropDown(anchor, popupHorizontalOffset, popupVerticalOffset)
	}

	fun dismiss() {
		popup.dismiss()
	}
}