package com.ionos.scanbot.screens.save.use_case.save

import android.net.Uri
import com.ionos.scanbot.exception.*
import com.ionos.scanbot.exception.SaveDocumentException
import com.ionos.scanbot.provider.SdkProvider
import com.ionos.scanbot.provider.SupportedLanguagesProvider
import com.ionos.scanbot.repository.bitmap.BitmapRepository
import com.ionos.scanbot.repository.RepositoryFacade
import com.ionos.scanbot.screens.save.use_case.name.GetPdfFileName
import io.reactivex.Single
import io.scanbot.pdf.model.PageDirection
import io.scanbot.pdf.model.PageSize
import io.scanbot.pdf.model.PdfAttributes
import io.scanbot.pdf.model.PdfConfig
import io.scanbot.sdk.blob.BlobType
import java.io.File
import javax.inject.Inject

internal class SavePdf @Inject constructor(
	private val sdkProvider: SdkProvider,
	private val bitmapRepository: BitmapRepository,
	private val repositoryFacade: RepositoryFacade,
	private val supportedLanguagesProvider: SupportedLanguagesProvider,
	private val getPdfFileName: GetPdfFileName,
) {
	private val pdfRenderer by lazy { sdkProvider.get().createPdfRenderer() }
	private val ocrRecognizer by lazy { sdkProvider.get().createOcrRecognizer() }
	private val blobManager by lazy { sdkProvider.get().createBlobManager() }
	private val blobFactory by lazy { sdkProvider.get().createBlobFactory() }
	private val supportedLanguages by lazy { supportedLanguagesProvider.get() }
	private val pdfConfig by lazy { PdfConfig(pageSize = PageSize.A4, pdfAttributes = PdfAttributes.defaultAttributes(), pageDirection = PageDirection.AUTO) }

	operator fun invoke(baseFileName: String, withOcr: Boolean): Single<List<Uri>> = readImageFiles()
		.map { createPdf(it, baseFileName, withOcr = withOcr && ocrBlobsAvailable()) }
		.doFinally { sdkProvider.get().createPageFileStorage().removeAll() }
		.onErrorResumeNext { Single.error(SaveDocumentException(it)) }

	private fun readImageFiles(): Single<List<Uri>> = Single
		.fromCallable { repositoryFacade.readAll() }
		.flattenAsObservable { it }
		.map { bitmapRepository.readFile(it.modified.id) }
		.map { Uri.fromFile(it) }
		.toList()

	private fun createPdf(imageFileUris: List<Uri>, baseFileName: String, withOcr: Boolean): List<Uri> {
		val pdfFile = if (withOcr) {
			recognizeDocument(imageFileUris)
		} else {
			renderDocument(imageFileUris)
		}
		val resultFile = File(pdfFile.parent, getPdfFileName(baseFileName))
		pdfFile.renameTo(resultFile)
		return listOf(Uri.fromFile(resultFile))
	}

	private fun recognizeDocument(imageFileUris: List<Uri>): File {
		val result = ocrRecognizer.recognizeTextWithPdfFromUris(
			imageFileUris = imageFileUris,
			sourceFilesEncrypted = false,
			pdfConfig = pdfConfig
		)
		return result.sandwichedPdfDocumentFile ?: throw RecognizeDocumentException()
	}

	private fun renderDocument(imageFileUris: List<Uri>): File {
		val pdfFile = pdfRenderer.renderDocumentFromImages(
			imageFileUris = imageFileUris,
			sourceFilesEncrypted = false,
			pdfConfig = pdfConfig
		)
		return pdfFile ?: throw RenderDocumentException()
	}

	private fun ocrBlobsAvailable(): Boolean {
		blobFactory.getBlobsForType(BlobType.OCR_BLOBS)
		val languagesWithAvailableOcrBlobs = blobManager.allLanguagesWithAvailableOcrBlobs
		return supportedLanguages.all { languagesWithAvailableOcrBlobs.contains(it) }
	}
}
