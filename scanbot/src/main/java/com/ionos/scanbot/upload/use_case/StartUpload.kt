package com.ionos.scanbot.upload.use_case

import android.net.Uri
import javax.inject.Inject

internal class StartUpload @Inject constructor(
	private val uploader: Uploader,
){
	operator fun invoke(localUris: List<Uri>, remotePath: String) {
        uploader.upload(
            remotePath,
            localUris.map { uri -> uri.path.orEmpty() })
    //TODO alk - Implement start of uploading of file here
        // upload.startUploadWithoutCheckForFileExisting(
		// 	localUris,
		// 	remotePath,
		// 	Optional.absent(),
		// 	{ createListener(it, onResult) },
		// 	NullParamAction(),
		// )
	}
}
