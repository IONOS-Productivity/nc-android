package com.strato.hidrive.scanbot.screens.common.use_case.open_screen

import android.app.Activity
import android.content.Intent
import com.strato.hidrive.scanbot.screens.common.use_case.open_screen.OpenScreenIntent.*
import com.strato.hidrive.scanbot.screens.camera.CameraActivity
import com.strato.hidrive.scanbot.screens.crop.CropActivity
import com.strato.hidrive.scanbot.screens.filter.FilterActivity
import com.strato.hidrive.scanbot.screens.gallery.GalleryActivity
import com.strato.hidrive.scanbot.screens.rearrange.RearrangeActivity
import com.strato.hidrive.scanbot.screens.save.SaveActivity

internal class OpenScreen(private val activity: Activity) {

	operator fun invoke(intent: OpenScreenIntent) {
		activity.startActivity(intent.toAndroidIntent())
		if (intent.closeCurrent) {
			activity.finish()
		}
	}

	private fun OpenScreenIntent.toAndroidIntent(): Intent = when (this) {
		is OpenCameraScreenIntent -> Intent(activity, CameraActivity::class.java)
		is OpenGalleryScreenIntent -> GalleryActivity.createIntent(activity, pictureId)
		is OpenCropScreenIntent -> CropActivity.createIntent(activity, pictureId)
		is OpenFilterScreenIntent -> FilterActivity.createIntent(activity, pictureId, filterType)
		is OpenRearrangeScreenIntent -> Intent(activity, RearrangeActivity::class.java)
		is OpenSaveScreenIntent -> Intent(activity, SaveActivity::class.java)
	}
}
