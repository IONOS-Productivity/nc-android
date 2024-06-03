package com.ionos.scanbot.tracking

interface ScanbotSaveScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackExitConfirmed()

	fun trackExitDenied()

	fun trackSaveClicked()

	fun trackFileNameChanged()

	fun trackSaveLocationPathClicked()

	fun trackSaveLocationPathChanged()

	fun trackSaveLocationPathChangeCanceled()

	fun trackPdfOcrFileTypeSelected()

	fun trackPdfFileTypeSelected()

	fun trackJpgFileTypeSelected()

	fun trackPngFileTypeSelected()
}
