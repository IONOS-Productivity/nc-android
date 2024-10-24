package com.ionos.scanbot.upload.target_provider

import java.io.Serializable

/**
 * User: Dima Muravyov
 * Date: 27.05.2019
 */
interface UploadTarget: Serializable {

    val uploadPath: String

}

data class ScanbotUploadTarget(
    override val uploadPath: String,
): UploadTarget