package com.ionos.scanbot.tracking

interface ScanbotRearrangeScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackPictureDragged()
}
