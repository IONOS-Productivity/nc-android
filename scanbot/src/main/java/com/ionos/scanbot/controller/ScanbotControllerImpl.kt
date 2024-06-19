package com.ionos.scanbot.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ionos.scanbot.repository.PictureRepository
import com.ionos.scanbot.repository.RepositoryFacade
import com.ionos.scanbot.screens.camera.CameraActivity
import com.ionos.scanbot.upload.target_provider.ScanbotUploadTarget
import com.ionos.scanbot.upload.target_provider.UploadTarget
import com.ionos.scanbot.upload.target_provider.UploadTargetRepositoryImpl
import com.ionos.scanbot.upload.use_case.StartUpload
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

//todo alk - probably get rid of scanbot controller

@Singleton
class ScanbotControllerImpl @Inject internal constructor(
	private val startUpload: StartUpload,
	private val pictureRepository: PictureRepository,
	private val repositoryFacade: RepositoryFacade,
) : ScanbotController() {

	override val fileUploadStarted get() = _fileUploadStarted
	override val uploadTargetRepository get() = _uploadTargetRepository

	private val _fileUploadStarted = PublishSubject.create<Any>()
	private val stateManager = StateManager()

	private var _uploadTargetRepository = UploadTargetRepositoryImpl(ScanbotUploadTarget(""))

	override fun setUpController(scanBotUploadTarget: UploadTarget) {
		_uploadTargetRepository = UploadTargetRepositoryImpl(scanBotUploadTarget)
	}

	override fun createIntent(context: Context): Intent {
		return Intent(context, CameraActivity::class.java)
			.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
	}

	override fun scanToDocument(context: Context, path: String) {
        startCameraActivity(context, ScanbotUploadTarget(path))
	}

	override fun saveState(state: Bundle) {
		//if (::_uploadTargetRepository.isInitialized) {
			stateManager.saveUploadTarget(uploadTargetRepository.uploadTarget, state)
		//}
		if (!pictureRepository.isEmpty()) {
			stateManager.savePictures(pictureRepository.readAll(), state)
		}
	}

	override fun restoreState(state: Bundle) {
		//if (!::_uploadTargetRepository.isInitialized) {
			stateManager.restoreUploadTarget(state) { _uploadTargetRepository = UploadTargetRepositoryImpl(it) }
		//}
		if (pictureRepository.isEmpty()) {
			stateManager.restorePictures(state) { pictureRepository.create(it) }
		}
	}

	override fun onDocumentSaved(localUris: List<Uri>) {
		val remotePath = uploadTargetRepository.uploadTarget.uploadPath
		startUpload(localUris, remotePath)
		_fileUploadStarted.onNext(Any())
		repositoryFacade.release()
	}

	private fun startCameraActivity(context: Context, uploadTarget: UploadTarget) {
		setUpController(uploadTarget)
		context.startActivity(createIntent(context))
	}
}
