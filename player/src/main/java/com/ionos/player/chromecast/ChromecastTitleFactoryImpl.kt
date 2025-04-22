package com.ionos.player.chromecast

import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.transformation.FileInfoToDisplayNameTransformation
import com.ionos.player.transformation.FileInfoToStringSizeTransformation
import javax.inject.Inject

class ChromecastTitleFactoryImpl @Inject constructor(
	private val fileInfoToStringSizeTransformation: FileInfoToStringSizeTransformation,
	private val fileInfoToDisplayNameTransformation: FileInfoToDisplayNameTransformation,
): ChromecastTitleFactory {

	override fun getTitle(fileInfo: PlayerFileInfo): String {
		return fileInfoToDisplayNameTransformation.transform(fileInfo)
	}

	override fun getSubtitle(fileInfo: PlayerFileInfo): String {
		val textBuilder = StringBuilder()
		if (fileInfo.contentLength != 0L) {
			textBuilder.append(
				fileInfoToStringSizeTransformation.transform(fileInfo)
			)
		}
		return textBuilder.toString()
	}
}