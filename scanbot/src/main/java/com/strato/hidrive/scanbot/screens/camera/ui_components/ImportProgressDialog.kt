package com.strato.hidrive.scanbot.screens.camera.ui_components

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import com.ionos.common_ui.dialog.SafeProgressDialog
import com.strato.hidrive.scanbot.R

internal class ImportProgressDialog(
	context: Context,
	private val onCancelClick: () -> Unit,
) {
	private val safeProgressDialog = SafeProgressDialog(context)

	init {
		safeProgressDialog.init()
	}

	fun show(progress: Int) {
		safeProgressDialog.progress = progress
		safeProgressDialog.show()
	}

	fun dismiss() {
		safeProgressDialog.dismiss()
	}

	private fun SafeProgressDialog.init() {
		isIndeterminate = false
		setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
		setTitle(context.getString(R.string.scanbot_processing_title))
		max = 100
		setCancelable(false)
		setCanceledOnTouchOutside(false)
		setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel_btn_title)) { dialog, _ ->
			dialog.dismiss()
			onCancelClick()
		}
	}
}
