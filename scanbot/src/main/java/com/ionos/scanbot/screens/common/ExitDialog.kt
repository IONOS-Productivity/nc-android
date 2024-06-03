package com.ionos.scanbot.screens.common

import android.content.Context
import com.ionos.common_ui.dialog.wrapper.SingleMessageDialogWrapper
import com.ionos.common_ui.utils.ContextUtils
import com.ionos.domain.exception.TryCatchExceptionHandler
import com.ionos.scanbot.R
import javax.inject.Inject

internal class ExitDialog @Inject constructor(
	private val tryCatchExceptionHandler: TryCatchExceptionHandler,
) {

	fun show(context: Context, onConfirmed: () -> Unit, onDenied: () -> Unit = {}) {
		if (!ContextUtils.isActivityFinishing(context)) {
			SingleMessageDialogWrapper(
				context,
				tryCatchExceptionHandler,
				R.string.scanbot_exit_confirmation_title,
				R.string.scanbot_exit_confirmation_message,
				R.string.ok_btn_title,
				R.string.cancel_btn_title,
				onConfirmed::invoke,
				onDenied::invoke,
			).show()
		}
	}
}
