package com.ionos.scanbot.tracking

interface ScanbotCropScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackSaveClicked()

	fun trackDetectDocumentClicked()

	fun trackResetBordersClicked()
}
