package com.strato.hidrive.scanbot.screens.save.use_case.name

import javax.inject.Inject

internal class GetPdfFileName @Inject constructor() {

	companion object {
		private const val PDF_EXTENSION = "pdf"
	}

	operator fun invoke(baseFileName: String): String = when {
		baseFileName.endsWith(".$PDF_EXTENSION") -> baseFileName
		else -> "$baseFileName.$PDF_EXTENSION"
	}
}
