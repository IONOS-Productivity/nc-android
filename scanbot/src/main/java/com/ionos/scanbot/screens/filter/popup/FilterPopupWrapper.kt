package com.ionos.scanbot.screens.filter.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.StringRes
import com.ionos.scanbot.R
import com.ionos.scanbot.databinding.ScanbotFilterPopupBinding

internal class FilterPopupWrapper(context: Context, onItemClick: ()->Unit) {
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

	private fun configureItem(@StringRes titleRes: Int, onItemClick: ()->Unit) {
		binding.tvPopupText.setText(titleRes)
		binding.tvPopupText.setOnClickListener {
			onItemClick()
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