package com.ionos.scanbot.screens.save.use_case

import com.ionos.domain.upload.targetprovider.UploadTarget
import io.reactivex.Single
import javax.inject.Inject

internal class GetTargetPath @Inject constructor(
) {

    // transform a path to the appropriate form here if it is needed
    operator fun invoke(uploadTarget: UploadTarget): Single<String> = when (uploadTarget) {
        is UploadTarget.RemotePath -> Single.just(uploadTarget.remotePath)
        is UploadTarget.PublicPath -> Single.just(uploadTarget.remotePath)
    }
}
