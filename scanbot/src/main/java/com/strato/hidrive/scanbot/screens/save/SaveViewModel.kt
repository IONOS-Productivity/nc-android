package com.strato.hidrive.scanbot.screens.save

import android.net.Uri
import com.strato.hidrive.domain.upload.target.UploadTarget
import com.strato.hidrive.domain.utils.kotlin.extension.plusAssign
import com.strato.hidrive.scanbot.controller.ScanbotController
import com.strato.hidrive.scanbot.provider.DocumentNameProvider
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import com.strato.hidrive.scanbot.screens.base.BaseViewModel
import com.strato.hidrive.scanbot.screens.save.SaveScreen.*
import com.strato.hidrive.scanbot.screens.save.SaveScreen.Event.*
import com.strato.hidrive.scanbot.screens.save.use_case.ValidateFilesForUpload
import com.strato.hidrive.scanbot.screens.save.use_case.save.SaveDocument
import com.strato.hidrive.scanbot.tracking.ScanbotSaveScreenEventTracker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class SaveViewModel @Inject constructor(
	private val saveDocument: SaveDocument,
    // TODO alk
	// private val getTargetPath: GetTargetPath,
	private val validateFiles: ValidateFilesForUpload,
	private val scanbotController: ScanbotController,
	private val repositoryFacade: RepositoryFacade,
	// private val eventTracker: ScanbotSaveScreenEventTracker,
	documentNameProvider: DocumentNameProvider,
) : BaseViewModel<Event, State>(State(documentNameProvider.getName()), /*eventTracker*/),
	ViewModel {

	companion object {
		private const val USER_STOP_TYPING_TIMEOUT = 1000L
	}

	private val fileNameChangedSubject = PublishSubject.create<String>()
	private var uploadTarget by scanbotController.uploadTargetRepository::uploadTarget

	init {
		updateState { copy(processing = true) }
		updateTargetPath(uploadTarget)

		// subscriptions += fileNameChangedSubject
		// 	.throttleWithTimeout(USER_STOP_TYPING_TIMEOUT, TimeUnit.MILLISECONDS)
		// 	.subscribe { eventTracker.trackFileNameChanged() }
	}

	override fun onCleared() {
		super.onCleared()
		repositoryFacade.release()
	}

	override fun onEventHandled() {
		updateState { copy(event = null) }
	}

	override fun onBackPressed() {
		// eventTracker.trackBackPressed()
		updateState { copy(event = ShowExitDialogEvent) }
	}

	override fun onExitConfirmed() {
		// eventTracker.trackExitConfirmed()
		updateState { copy(event = CloseScreenEvent) }
	}

	override fun onExitDenied() {
		// eventTracker.trackExitDenied()
	}

	override fun onFileNameChanged(baseFileName: String) {
		if (state().baseFileName != baseFileName) {
			fileNameChangedSubject.onNext(baseFileName)
			updateState { copy(baseFileName = baseFileName) }
		}
	}

	override fun onFileTypeChanged(fileType: FileType) {
		if (state().fileType != fileType) {
			// when (fileType) {
			// 	FileType.PDF_OCR -> eventTracker.trackPdfOcrFileTypeSelected()
			// 	FileType.PDF -> eventTracker.trackPdfFileTypeSelected()
			// 	FileType.JPG -> eventTracker.trackJpgFileTypeSelected()
			// 	FileType.PNG -> eventTracker.trackPngFileTypeSelected()
			// }
			updateState { copy(fileType = fileType) }
		}
	}

	override fun onSaveLocationPathClicked() {
		// eventTracker.trackSaveLocationPathClicked()
		updateState { copy(event = LaunchUploadTargetPickerEvent(uploadTarget, state().baseFileName)) }
	}

	override fun onUploadTargetPicked(uploadTarget: UploadTarget) {
		// eventTracker.trackSaveLocationPathChanged()
		this.uploadTarget = uploadTarget
		updateState { copy(processing = true) }
		updateTargetPath(uploadTarget)
	}

	override fun onUploadTargetPickerCanceled() {
		// eventTracker.trackSaveLocationPathChangeCanceled()
	}

	override fun onSaveClicked() {
		// eventTracker.trackSaveClicked()

		updateState { copy(processing = true) }

		subscriptions += validateFiles(uploadTarget, state().baseFileName, state().fileType)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(::saveDocument, ::onError)
	}

	override fun onOverwriteDialogsResult(overwritePaths: List<String>, allowOverwritePaths: List<String>) {
		if (allowOverwritePaths.containsAll(overwritePaths)) {
			updateState { copy(processing = true) }
			saveDocument()
		}
	}

	private fun updateTargetPath(uploadTarget: UploadTarget) {
		// subscriptions += getTargetPath(uploadTarget)
		// 	.subscribeOn(Schedulers.io())
		// 	.observeOn(AndroidSchedulers.mainThread())
		// 	.subscribe(::onTargetPathUpdated, ::onError)
	}

	private fun saveDocument() {
		subscriptions += saveDocument(state().baseFileName, state().fileType)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(::onDocumentSaved, ::onError)
	}

	private fun onTargetPathUpdated(targetPath: String) {
		updateState { copy(processing = false, targetPath = targetPath) }
	}

	private fun onDocumentSaved(uris: List<Uri>) {
		scanbotController.onDocumentSaved(uris)
		updateState { copy(processing = false, event = CloseScreenEvent) }
	}

	private fun onError(error: Throwable) {
		updateState { copy(processing = false, event = HandleErrorEvent(error)) }
	}
}
