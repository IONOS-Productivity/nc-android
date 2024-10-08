package com.ionos.scanbot.screens.camera.ui_components

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ionos.common_ui.utils.ContextUtils
import com.ionos.scanbot.R
import com.ionos.scanbot.di.inject
import kotlin.getValue

class NoFreeSpaceMessageDialogWrapper(context: Context) {
    private val tryCatchExceptionHandler by context.inject { tryCatchExceptionHandler() }

    private val dialog by lazy {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.no_enough_free_device_space_title)
            .setMessage(R.string.scanbot_no_enough_free_device_space_message)
            .setPositiveButton(R.string.ok_btn_title) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    fun show(context: Context?) {
        if (!ContextUtils.isActivityFinishing(context)) {
            tryCatchExceptionHandler.handle(dialog::show)
        }
    }
}
