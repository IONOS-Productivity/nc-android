package com.ionos.scanbot.screens.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ionos.common_ui.utils.ContextUtils
import com.ionos.common_ui.exception.TryCatchExceptionHandler
import com.ionos.scanbot.R
import javax.inject.Inject

internal class ExitDialog @Inject constructor(
	private val tryCatchExceptionHandler: TryCatchExceptionHandler,
) {

	fun show(context: Context, onConfirmed: () -> Unit, onDenied: () -> Unit = {}) {
		if (!ContextUtils.isActivityFinishing(context)) {
			val dialog = createDialog(context, onConfirmed, onDenied)
            tryCatchExceptionHandler.handle(dialog::show)
		}
	}

    private fun createDialog(context: Context, onConfirmed: () -> Unit, onDenied: () -> Unit): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(R.string.scanbot_exit_confirmation_title)
            .setMessage(R.string.scanbot_exit_confirmation_message)
            .setPositiveButton(R.string.scanbot_ok_btn_title) { dialog, _ ->
                dialog.dismiss()
                onConfirmed()
            }
            .setNegativeButton(R.string.scanbot_cancel_btn_title) { dialog, _ ->
                dialog.dismiss()
                onDenied()
            }
            .create()
    }
}
