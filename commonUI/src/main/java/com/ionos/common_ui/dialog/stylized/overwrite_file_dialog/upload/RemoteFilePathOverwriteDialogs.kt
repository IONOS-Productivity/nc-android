package com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.upload

import android.app.Activity
import com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.DialogWrapperCreator
import com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.OverwriteDialogs
import com.strato.hidrive.domain.interfaces.actions.ParamAction
import java.io.File

class RemoteFilePathOverwriteDialogs(
	entitiesToProceed: List<String>,
	activity: Activity,
	onEachSingleDialogClosed: ParamAction<String>,
) : OverwriteDialogs<String>(entitiesToProceed, activity, onEachSingleDialogClosed) {

	override fun getWrapperCreator() = object : DialogWrapperCreator<String>(
		activity,
		dialogsCounter,
		entitiesForOverride,
		entitiesForDialogsQueue,
		onEachSingleDialogClosed,
	) {
		override fun getFileName(entityForOverride: String): String {
			return File(entityForOverride).name
		}
	}
}
