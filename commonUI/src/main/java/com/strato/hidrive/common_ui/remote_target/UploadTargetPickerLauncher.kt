package com.strato.hidrive.common_ui.remote_target

import androidx.activity.ComponentActivity
import com.strato.hidrive.domain.upload.target.UploadTarget

interface UploadTargetPickerLauncher {

	fun init(activity: ComponentActivity, onPicked: (UploadTarget) -> Unit, onCanceled: () -> Unit)

	fun launch(initialTarget: UploadTarget, fileName: String)
}