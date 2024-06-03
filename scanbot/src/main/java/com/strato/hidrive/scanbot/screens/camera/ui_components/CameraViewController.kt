package com.strato.hidrive.scanbot.screens.camera.ui_components

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.ionos.domain.utils.kotlin.extension.plusAssign
import com.strato.hidrive.scanbot.provider.ContourDetectorParamsProvider
import com.strato.hidrive.scanbot.screens.camera.use_case.GetUserGuidanceMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.scanbot.sdk.ScanbotSDK
import io.scanbot.sdk.camera.*
import io.scanbot.sdk.contourdetector.ContourDetectorFrameHandler
import io.scanbot.sdk.contourdetector.DocumentAutoSnappingController
import io.scanbot.sdk.ui.PolygonView
import java.util.concurrent.TimeUnit

internal class CameraViewController(
	private val activity: Activity,
	private val cameraView: ScanbotCameraView,
	private val polygonView: PolygonView,
	private val onPictureReceived: ((ByteArray, Int) -> Unit),
) {
	companion object {
		private const val SHOW_GUIDANCE_PERIOD = 3L
	}

	private val contourDetector = ScanbotSDK(activity).createContourDetector()
	private val contourDetectorFrameHandler = ContourDetectorFrameHandler.attach(cameraView, contourDetector)
	private val contourDetectorParamsProvider = ContourDetectorParamsProvider()

	private val autoSnappingController = DocumentAutoSnappingController(cameraView, contourDetectorFrameHandler)

	private val getUserGuidanceMessage = GetUserGuidanceMessage(activity)
	private val userGuidanceMessages: PublishSubject<String> = PublishSubject.create()
	private val userGuidanceDisposable = CompositeDisposable()
	private var userGuidanceToast: Toast? = null

	private var isOnPause = true

	init {
		cameraView.init()
		contourDetectorFrameHandler.init()
	}

	fun onResume() {
		if (isOnPause) {
			cameraView.resume()
			contourDetectorFrameHandler.resume()
			autoSnappingController.resume()
			isOnPause = false
		}
	}

	fun onPause() {
		if (!isOnPause) {
			isOnPause = true
			autoSnappingController.pause()
			contourDetectorFrameHandler.pause()
			cameraView.pause()
		}
	}

	private fun ScanbotCameraView.init() {
		setPreviewMode(CameraPreviewMode.FILL_IN)
		setCameraOpenCallback {
			setAutoFocusSound(false)
			setShutterSound(false)
			continuousFocus()
		}
	}

	private fun ScanbotCameraView.resume() {
		addPictureCallback(pictureReceivedCallback)
		onResume()
	}

	private fun ScanbotCameraView.pause() {
		onPause()
		removePictureCallback(pictureReceivedCallback)
	}

	private fun ContourDetectorFrameHandler.init() {
		setAcceptedAngleScore(contourDetectorParamsProvider.acceptedAngleScore)
		setAcceptedSizeScore(contourDetectorParamsProvider.acceptedSizeScore)
	}

	private fun ContourDetectorFrameHandler.resume() {
		addResultHandler(polygonView.contourDetectorResultHandler)
		isEnabled = true
	}

	private fun ContourDetectorFrameHandler.pause() {
		isEnabled = false
		removeResultHandler(polygonView.contourDetectorResultHandler)
	}

	private fun DocumentAutoSnappingController.resume() {
		if (isEnabled) {
			subscribeToUserGuidanceMessages()
		}
	}

	private fun DocumentAutoSnappingController.pause() {
		if (isEnabled) {
			unsubscribeFromUserGuidanceMessages()
		}
	}

	fun setAutoSnappingEnabled(enabled: Boolean) {
		if (autoSnappingController.isEnabled != enabled) {
			autoSnappingController.isEnabled = enabled
			if (enabled) {
				subscribeToUserGuidanceMessages()
			} else {
				unsubscribeFromUserGuidanceMessages()
			}
		}
	}

	fun setFlashEnabled(enabled: Boolean) {
		// run catching due to bug in scanbot sdk
		val isFlashEnabled = runCatching(cameraView::isFlashEnabled).getOrDefault(!enabled)
		if (isFlashEnabled != enabled) {
			cameraView.useFlash(enabled)
		}
	}

	fun takePicture() {
		cameraView.takePicture(false)
	}

	private fun subscribeToUserGuidanceMessages() {
		contourDetectorFrameHandler.addResultHandler(userGuidanceListener)
		userGuidanceDisposable += userGuidanceMessages
			.sample(SHOW_GUIDANCE_PERIOD, TimeUnit.SECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext { userGuidanceToast?.cancel() }
			.doOnDispose { userGuidanceToast?.cancel() }
			.subscribe(::showUserGuidanceToast)
	}

	private fun unsubscribeFromUserGuidanceMessages() {
		contourDetectorFrameHandler.removeResultHandler(userGuidanceListener)
		userGuidanceDisposable.clear()
	}

	private fun showUserGuidanceToast(message: String) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).apply {
			userGuidanceToast = this
			setGravity(Gravity.CENTER, 0, 0)
			show()
		}
	}

	private val userGuidanceListener = ContourDetectorFrameHandler.ResultHandler { result ->
		if (result is FrameHandlerResult.Success) {
			val status = result.value.detectionStatus
			getUserGuidanceMessage(status)?.let { message ->
				userGuidanceMessages.onNext(message)
			}
		}
		false
	}

	private val pictureReceivedCallback: PictureCallback = object : PictureCallback() {
		override fun onPictureTaken(image: ByteArray, captureInfo: CaptureInfo) {
			cameraView.post {
				onPictureReceived(image, captureInfo.imageOrientation)
				setAutoSnappingEnabled(false)
				setFlashEnabled(false)
			}
		}
	}
}
