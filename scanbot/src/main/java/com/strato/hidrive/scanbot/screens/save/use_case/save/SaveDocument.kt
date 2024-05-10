package com.strato.hidrive.scanbot.screens.save.use_case.save

import android.net.Uri
import com.strato.hidrive.scanbot.screens.save.SaveScreen.FileType
import com.strato.hidrive.scanbot.entity.ImageFormat
import io.reactivex.Single
import javax.inject.Inject

internal class SaveDocument @Inject constructor(
	private val savePdf: SavePdf,
	private val saveImages: SaveImages,
) {

	operator fun invoke(baseFileName: String, fileType: FileType): Single<List<Uri>> = when (fileType) {
		FileType.PDF_OCR -> savePdf(baseFileName, withOcr = true)
		FileType.PDF -> savePdf(baseFileName, withOcr = false)
		FileType.JPG -> saveImages(baseFileName, ImageFormat.JPEG)
		FileType.PNG -> saveImages(baseFileName, ImageFormat.PNG)
	}
}
