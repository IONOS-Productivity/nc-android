package com.ionos.common_ui.di

import android.content.Context
import com.ionos.common_ui.dialog.SafeProgressDialog
import com.ionos.common_ui.dialog.wrapper.NoFreeSpaceMessageDialogWrapper
import com.ionos.common_ui.dialog.wrapper.OverwriteFileDialogWrapper

interface CommonUiComponent {

	fun inject(safeProgressDialog: SafeProgressDialog)

	fun inject(noFreeSpaceMessageDialogWrapper: NoFreeSpaceMessageDialogWrapper)

	fun inject(overwriteFileDialogWrapper: OverwriteFileDialogWrapper)

	companion object {
		@JvmStatic
		fun from(context: Context): CommonUiComponent {
			val provider = context.applicationContext as? CommonUiComponentProvider
			return provider?.getComponent() ?: throw IllegalStateException("No available context")
		}
	}
}
