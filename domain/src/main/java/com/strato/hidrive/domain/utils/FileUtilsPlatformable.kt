package com.strato.hidrive.domain.utils

import com.strato.hidrive.domain.entity.RemoteFileInfo

interface FileUtilsPlatformable {

	fun getExtension(fileInfo: RemoteFileInfo): String

}