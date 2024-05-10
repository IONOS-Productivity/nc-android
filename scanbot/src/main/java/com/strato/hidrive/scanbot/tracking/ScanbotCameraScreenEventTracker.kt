package com.strato.hidrive.scanbot.tracking

interface ScanbotCameraScreenEventTracker : ScanbotScreenEventTracker {

	fun trackCancelClicked()

	fun trackBackPressed()

	fun trackExitConfirmed()

	fun trackExitDenied()

	fun trackAutomaticCaptureToggled(enabled: Boolean)

	fun trackFlashToggled(enabled: Boolean)

	fun trackImportClicked()

	fun trackImportCanceled()

	fun trackTakePictureClicked()
}
