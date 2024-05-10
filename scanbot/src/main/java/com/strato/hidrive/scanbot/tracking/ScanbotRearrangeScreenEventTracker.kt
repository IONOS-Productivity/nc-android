package com.strato.hidrive.scanbot.tracking

interface ScanbotRearrangeScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackPictureDragged()
}
