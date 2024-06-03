package com.strato.hidrive.scanbot.upload.use_case

import android.net.Uri
import androidx.core.net.toFile
import com.ionos.domain.background.jobs.interfaces.UploadJobListener
import com.ionos.domain.entity.RemoteFileInfo
import com.ionos.domain.interfaces.actions.null_objects.NullParamAction
import com.ionos.domain.optional.Optional
import com.ionos.domain.upload.result.UploadFileResult
import com.ionos.domain.utils.kotlin.extension.deleteIfExists
import com.strato.hidrive.scanbot.upload.UploadWithoutCheckForFileExisting
import java.io.File
import java.util.Collections
import javax.inject.Inject

//TODO alk - make the class internal
class StartUpload @Inject constructor(
	//private val upload: UploadWithoutCheckForFileExisting,
	// private val thumbnailCacheCleaner: EntityCacheCleaner<RemoteFileInfo>,
) {
	private val listeners: MutableSet<UploadJobListener> = Collections.synchronizedSet(hashSetOf())

	operator fun invoke(localUris: List<Uri>, remotePath: String, onResult: (UploadFileResult) -> Unit) {
		//TODO alk - Implement start of uploading of file here
        // upload.startUploadWithoutCheckForFileExisting(
		// 	localUris,
		// 	remotePath,
		// 	Optional.absent(),
		// 	{ createListener(it, onResult) },
		// 	NullParamAction(),
		// )
	}

	private fun createListener(uri: Uri, onResult: (UploadFileResult) -> Unit) = object : UploadJobListener {
		init {
			listeners.add(this)
		}

		override fun onUploadFileFail(error: Throwable) {
			onResult(UploadFileResult.Error(error))
		}

		override fun onUploadFileSuccess(localFilePath: String, uploadedFile: RemoteFileInfo) {
			//TODO alk - clean a cache here
            //thumbnailCacheCleaner.clean(uploadedFile)
			//onResult(UploadFileResult.Success(uploadedFile))
			File(localFilePath).deleteIfExists()
		}

		override fun onUploadComplete() {
			uri.toFile().deleteIfExists()
			listeners.remove(this)
		}
	}
}
