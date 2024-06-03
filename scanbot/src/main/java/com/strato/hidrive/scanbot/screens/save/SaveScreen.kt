package com.strato.hidrive.scanbot.screens.save

import com.ionos.domain.upload.targetprovider.UploadTarget
import com.strato.hidrive.scanbot.screens.base.BaseScreen

internal interface SaveScreen {

	data class State(
		val baseFileName: String,
		val fileType: FileType = FileType.PDF_OCR,
		val targetPath: String = "",
		val processing: Boolean = false,
		override val event: Event? = null,
	) : BaseScreen.State<Event>

	enum class FileType { PDF_OCR, PDF, JPG, PNG }

	sealed interface Event : BaseScreen.Event {
		data class LaunchUploadTargetPickerEvent(val initialTarget: UploadTarget, val fileName: String) : Event
		data class HandleErrorEvent(val error: Throwable) : Event
		object ShowExitDialogEvent : Event
		object CloseScreenEvent : Event
	}

	interface ViewModel : BaseScreen.ViewModel<Event, State> {

		fun onBackPressed()

		fun onExitConfirmed()

		fun onExitDenied()

		fun onFileNameChanged(baseFileName: String)

		fun onFileTypeChanged(fileType: FileType)

		fun onSaveLocationPathClicked()

		fun onUploadTargetPicked(uploadTarget: UploadTarget)

		fun onUploadTargetPickerCanceled()

		fun onSaveClicked()

		fun onOverwriteDialogsResult(overwritePaths: List<String>, allowOverwritePaths: List<String>)
	}
}
