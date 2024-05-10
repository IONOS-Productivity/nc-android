package com.strato.hidrive.common_ui.dialog.wrapper

interface OverwriteFileDialogEventTracker {

	fun trackPage()

	fun trackOverwriteClicked()

	fun trackCancelClicked()

	fun trackApplyToAllCheckChanged(isChecked: Boolean)
}
