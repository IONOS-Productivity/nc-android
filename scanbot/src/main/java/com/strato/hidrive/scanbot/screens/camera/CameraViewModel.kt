package com.strato.hidrive.scanbot.screens.camera

import android.graphics.Bitmap
import android.net.Uri
import com.strato.hidrive.domain.permission.Permission
import com.strato.hidrive.domain.permission.PermissionsController
// import com.strato.hidrive.domain.use_case.free_space.GetLocalFreeSpace
import com.strato.hidrive.domain.utils.kotlin.extension.plusAssign
import com.strato.hidrive.scanbot.exception.NoCameraPermissionException
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import com.strato.hidrive.scanbot.repository.bitmap.BitmapDecoder
import com.strato.hidrive.scanbot.screens.base.BaseViewModel
import com.strato.hidrive.scanbot.screens.camera.CameraScreen.*
import com.strato.hidrive.scanbot.screens.camera.CameraScreen.Event.*
import com.strato.hidrive.scanbot.screens.camera.use_case.AddPictureToRepository
import com.strato.hidrive.scanbot.screens.camera.use_case.import_pictures.ImportPictures
import com.strato.hidrive.scanbot.screens.camera.use_case.import_pictures.ImportPicturesState
import com.strato.hidrive.scanbot.screens.camera.use_case.GetCameraScreenErrorMessage
import com.strato.hidrive.scanbot.screens.common.use_case.open_screen.OpenScreenIntent.OpenGalleryScreenIntent
import com.strato.hidrive.scanbot.tracking.ScanbotCameraScreenEventTracker
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

internal class CameraViewModel @Inject constructor(
	private val addPictureToRepository: AddPictureToRepository,
	private val importPictures: ImportPictures,
	//private val getLocalFreeSpace: GetLocalFreeSpace,
	private val bitmapDecoder: BitmapDecoder,
	private val repositoryFacade: RepositoryFacade,
	// private val permissionsController: PermissionsController,
	private val getErrorMessage: GetCameraScreenErrorMessage,
	// private val eventTracker: ScanbotCameraScreenEventTracker,
) : BaseViewModel<Event, State>(State()/*, eventTracker*/),
	ViewModel {

	companion object {
		private const val MIN_DEVICE_FREE_SPACE: Long = 50 * 1024 * 1024
	}

	private val importCancellation = PublishSubject.create<Boolean>()

	override fun onCreate() {
		//TODO alk
        // permissionsController.checkPermissions(Permission.ACCESS_CAMERA) { result ->
		// 	if (!result.isMandatoryPermissionsGranted) {
		// 		onError(NoCameraPermissionException())
		// 	}
		// }
	}

	override fun onCleared() {
		super.onCleared()
		importCancellation.onNext(true)
	}

	override fun onEventHandled() {
		updateState { copy(event = null) }
	}

	override fun onCancelClicked() {
		// eventTracker.trackCancelClicked()
		closeScreenOrShowConfirmationDialog()
	}

	override fun onBackPressed() {
		// eventTracker.trackBackPressed()
		closeScreenOrShowConfirmationDialog()
	}

	private fun closeScreenOrShowConfirmationDialog() {
		if (repositoryFacade.isEmpty()) {
			closeScreen()
		} else {
			updateState { copy(event = ShowExitDialogEvent) }
		}
	}

	override fun onExitConfirmed() {
		// eventTracker.trackExitConfirmed()
		subscriptions += Completable
			.fromCallable { repositoryFacade.release() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(::closeScreen, ::onError)
	}

	override fun onExitDenied() {
		// eventTracker.trackExitDenied()
	}

	private fun closeScreen() {
		updateState { copy(event = CloseScreenEvent) }
	}

	override fun onAutomaticCaptureToggled() {
		val enabled = !state.value.automaticCaptureEnabled
		// eventTracker.trackAutomaticCaptureToggled(enabled)
		updateState { copy(automaticCaptureEnabled = enabled) }
	}

	override fun onFlashToggled() {
		val enabled = !state.value.flashEnabled
		// eventTracker.trackFlashToggled(enabled)
		updateState { copy(flashEnabled = enabled) }
	}

	override fun onImportClicked() {
		// eventTracker.trackImportClicked()
		updateState { copy(event = LaunchImagePickerEvent) }
	}

	override fun onCancelImportClicked() {
		// eventTracker.trackImportCanceled()
		importCancellation.onNext(true)
	}

	override fun onTakePictureClicked() {
		// eventTracker.trackTakePictureClicked()
		// subscriptions += getLocalFreeSpace()
		// 	.subscribeOn(Schedulers.computation())
		// 	.observeOn(AndroidSchedulers.mainThread())
		// 	.subscribe(::takePictureIfDeviceHasEnoughFreeSpace, ::onError)
	}

	private fun takePictureIfDeviceHasEnoughFreeSpace(deviceFreeSpace: Long) {
		if (deviceFreeSpace > MIN_DEVICE_FREE_SPACE) {
			updateState { copy(event = TakePictureEvent) }
		} else {
			updateState { copy(event = ShowNoFreeSpaceAlertEvent) }
		}
	}

	override fun onPictureReceived(pictureBytes: ByteArray, pictureOrientation: Int) {
		updateState { copy(processing = Processing.Decoding) }

		subscriptions += decodeBitmap(pictureBytes, pictureOrientation)
			.map { addPictureToRepository(it) }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(::onPictureAdded, ::onError)
	}

	private fun onPictureAdded(pictureId: String) = updateState {
		val intent = OpenGalleryScreenIntent(pictureId, closeCurrent = true)
		copy(processing = Processing.None, event = OpenScreenEvent(intent))
	}

	private fun decodeBitmap(bytes: ByteArray, orientation: Int): Single<Bitmap> = Single.fromCallable {
		bitmapDecoder.decodeSampledBitmap(bytes, orientation)
	}

	override fun onPicturesUrisReceived(picturesUris: List<Uri>) {
		subscriptions += importPictures(picturesUris, importCancellation)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(::onNextImportState, ::onError)
	}

	private fun onNextImportState(importState: ImportPicturesState) = when (importState) {
		is ImportPicturesState.Processing -> {
			val progress = getProgress(importState.picturesUris.size, importState.processedItems.size)
			updateState { copy(processing = Processing.Import(progress)) }
		}

		is ImportPicturesState.Finished -> {
			val picturesId = importState.processedItems[0]
			val intent = OpenGalleryScreenIntent(picturesId, closeCurrent = true)
			updateState { copy(processing = Processing.None, event = OpenScreenEvent(intent)) }
		}

		is ImportPicturesState.Canceled -> {
			deletePictures(importState.processedItems)
			updateState { copy(processing = Processing.None) }
		}

		is ImportPicturesState.Error -> {
			deletePictures(importState.processedItems)
			onError(importState.error)
		}
	}

	private fun onError(error: Throwable) {
		val errorMessage = getErrorMessage(error)
		updateState { copy(processing = Processing.None, event = ShowMessageEvent(errorMessage)) }
	}

	private fun deletePictures(pictureIds: List<String>) {
		pictureIds.forEach { repositoryFacade.delete(it) }
	}

	private fun getProgress(total: Int, processed: Int): Int {
		return if (total != 0) (processed * 100) / total else 0
	}
}