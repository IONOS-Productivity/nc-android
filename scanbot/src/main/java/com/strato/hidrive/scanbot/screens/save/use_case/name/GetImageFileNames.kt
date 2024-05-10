package com.strato.hidrive.scanbot.screens.save.use_case.name

import com.strato.hidrive.scanbot.repository.RepositoryFacade
import com.strato.hidrive.scanbot.entity.ImageFormat
import javax.inject.Inject

internal class GetImageFileNames @Inject constructor(
	val repositoryFacade: RepositoryFacade,
) {

	operator fun invoke(baseFileName: String, imageFormat: ImageFormat, count: Int): List<String> {
		return List(count) { index ->
			val extension = imageFormat.extension
			val normalizedBaseFileName = normalizeBaseFileName(baseFileName, extension)
			if (count > 1) {
				"$normalizedBaseFileName${index + 1}.$extension"
			} else {
				"$normalizedBaseFileName.$extension"
			}
		}
	}

	private fun normalizeBaseFileName(baseFileName: String, extension: String): String = when {
		baseFileName.endsWith(".$extension") -> removeExtension(baseFileName, extension)
		else -> baseFileName
	}

	private fun removeExtension(baseFileName: String, extension: String): String {
		return baseFileName.substring(0, baseFileName.lastIndexOf(".$extension"))
	}
}
