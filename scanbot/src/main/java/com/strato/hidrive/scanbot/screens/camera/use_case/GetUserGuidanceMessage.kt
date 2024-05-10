package com.strato.hidrive.scanbot.screens.camera.use_case

import android.content.Context
import com.strato.hidrive.scanbot.R
import io.scanbot.sdk.core.contourdetector.DetectionStatus
import io.scanbot.sdk.core.contourdetector.DetectionStatus.*

internal class GetUserGuidanceMessage(private val context: Context) {

	operator fun invoke(status: DetectionStatus): String? = when (status) {
		OK -> context.getString(R.string.scanbot_camera_detection_result_ok)
		OK_BUT_TOO_SMALL -> context.getString(R.string.scanbot_camera_detection_result_ok_but_too_small)
		OK_BUT_BAD_ANGLES -> context.getString(R.string.scanbot_camera_detection_result_ok_but_bad_angles)
		ERROR_NOTHING_DETECTED -> context.getString(R.string.scanbot_camera_detection_result_error_nothing_detected)
		ERROR_TOO_NOISY -> context.getString(R.string.scanbot_camera_detection_result_error_too_noisy)
		ERROR_TOO_DARK -> context.getString(R.string.scanbot_camera_detection_result_error_too_dark)
		else -> null
	}
}