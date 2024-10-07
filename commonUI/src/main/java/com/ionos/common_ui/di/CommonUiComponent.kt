package com.ionos.common_ui.di

import android.content.Context
import com.ionos.common_ui.dialog.wrapper.NoFreeSpaceMessageDialogWrapper

interface CommonUiComponent {

	fun inject(noFreeSpaceMessageDialogWrapper: NoFreeSpaceMessageDialogWrapper)

	companion object {
		@JvmStatic
		fun from(context: Context): CommonUiComponent {
			val provider = context.applicationContext as? CommonUiComponentProvider
			return provider?.getComponent() ?: throw IllegalStateException("No available context")
		}
	}
}
