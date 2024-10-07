package com.ionos.scanbot.screens.camera.ui_components

import android.content.Context
import com.ionos.common_ui.dialog.stylized.StylizedDialog
import com.ionos.common_ui.dialog.stylized.localized.StringResLocalizedStrategy
import com.ionos.common_ui.utils.ContextUtils
import com.ionos.scanbot.R
import com.ionos.scanbot.di.inject
import kotlin.getValue

class NoFreeSpaceMessageDialogWrapper(context: Context) {
    private val tryCatchExceptionHandler by context.inject { tryCatchExceptionHandler() }

    private val dialog by lazy {
        StylizedDialog.newBuilder(this.tryCatchExceptionHandler)
            .setTitle(StringResLocalizedStrategy(context, R.string.no_enough_free_device_space_title))
            .setMessage(StringResLocalizedStrategy(context, R.string.scanbot_no_enough_free_device_space_message))
            .setPositiveButton(StringResLocalizedStrategy(context, R.string.ok_btn_title), StylizedDialog::dismiss)
            .build(context)
    }

    fun show(context: Context?) {
        if (!ContextUtils.isActivityFinishing(context)) {
            dialog.show()
        }
    }
}
