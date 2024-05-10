package com.strato.hidrive.common_ui.view.upload

import android.app.Activity

interface UploadMessageManagerSubscriber {

	fun subscribe(activity: Activity, showUploadScreenHandler: ShowUploadScreenHandler)

	fun unsubscribe()
}
