package com.strato.hidrive.scanbot.tracking

interface ScanbotCropScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackSaveClicked()

	fun trackDetectDocumentClicked()

	fun trackResetBordersClicked()
}
