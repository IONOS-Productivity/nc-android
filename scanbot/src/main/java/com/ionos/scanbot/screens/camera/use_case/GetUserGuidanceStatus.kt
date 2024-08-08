package com.ionos.scanbot.screens.camera.use_case

import android.content.Context
import com.ionos.scanbot.R
import io.scanbot.sdk.core.contourdetector.DetectionStatus
import io.scanbot.sdk.core.contourdetector.DetectionStatus.*

internal class GetUserGuidanceStatus(private val context: Context) {

	operator fun invoke(status: DetectionStatus): UserGuidanceStatus {
        return UserGuidanceStatus(statusText(status), status.icon())
	}

    private fun statusText(status: DetectionStatus): String? = when (status) {
		OK -> context.getString(R.string.scanbot_camera_detection_result_ok)
		OK_BUT_TOO_SMALL -> context.getString(R.string.scanbot_camera_detection_result_ok_but_too_small)
		OK_BUT_BAD_ANGLES -> context.getString(R.string.scanbot_camera_detection_result_ok_but_bad_angles)
		ERROR_NOTHING_DETECTED -> context.getString(R.string.scanbot_camera_detection_result_error_nothing_detected)
		ERROR_TOO_NOISY -> context.getString(R.string.scanbot_camera_detection_result_error_too_noisy)
		ERROR_TOO_DARK -> context.getString(R.string.scanbot_camera_detection_result_error_too_dark)
		else -> null
	}

    private fun DetectionStatus.icon() : Int? = when (this) {
        OK -> R.drawable.ic_dont_move
        OK_BUT_TOO_SMALL -> R.drawable.ic_zoom_in
        OK_BUT_BAD_ANGLES -> R.drawable.ic_bad_angles
        ERROR_NOTHING_DETECTED -> R.drawable.ic_no_file
        ERROR_TOO_NOISY -> R.drawable.ic_too_noisy
        ERROR_TOO_DARK -> R.drawable.ic_poor_light
        else -> null
    }
}