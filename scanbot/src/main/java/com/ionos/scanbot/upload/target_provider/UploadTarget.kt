package com.ionos.scanbot.upload.target_provider

import java.io.Serializable

/**
 * User: Dima Muravyov
 * Date: 27.05.2019
 */
sealed class UploadTarget(val remotePath: String) : Serializable {

	class RemotePath(remotePath: String) : UploadTarget(remotePath)

	class PublicPath(remotePath: String) : UploadTarget(remotePath)
}