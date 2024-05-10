package com.strato.hidrive.scanbot.tracking

interface ScanbotGalleryScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackExitConfirmed()

	fun trackExitDenied()

	fun trackSwipeNext()

	fun trackSwipeBack()

	fun trackSaveClicked()

	fun trackAddPictureClicked()

	fun trackCropClicked()

	fun trackFilterClicked()

	fun trackRotateClicked()

	fun trackRearrangeClicked()

	fun trackDeleteClicked()
}
