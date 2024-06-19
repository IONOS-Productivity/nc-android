package com.ionos.scanbot.upload.target

import com.ionos.scanbot.upload.target_provider.UploadTarget

class RemoteUploadTarget(
    override val uploadPath: String
) : UploadTarget