package com.strato.hidrive.scanbot.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ionos.domain.upload.result.UploadFileResult
import com.ionos.domain.upload.targetprovider.UploadTarget
import com.ionos.domain.upload.targetprovider.UploadTargetRepository
import io.reactivex.Observable

abstract class ScanbotController {
	abstract val fileUploaded: Observable<UploadFileResult>
	abstract val fileUploadStarted: Observable<Any>
	abstract val uploadTargetRepository: UploadTargetRepository

	abstract fun setUpController(scanBotUploadTarget: UploadTarget)

	abstract fun createIntent(context: Context): Intent

	abstract fun scanToDocument(context: Context, uploadTarget: UploadTarget)

	internal abstract fun saveState(state: Bundle)

	internal abstract fun restoreState(state: Bundle)

	internal abstract fun onDocumentSaved(localUris: List<Uri>)
}
