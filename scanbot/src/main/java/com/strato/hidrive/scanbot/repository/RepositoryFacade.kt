package com.strato.hidrive.scanbot.repository

import android.graphics.Bitmap
import com.strato.hidrive.scanbot.entity.ModifiedPicture
import com.strato.hidrive.scanbot.entity.Picture
import com.strato.hidrive.scanbot.entity.SelectedContour
import com.strato.hidrive.scanbot.filter.FilterType
import com.strato.hidrive.scanbot.provider.SdkProvider
import com.strato.hidrive.scanbot.repository.bitmap.BitmapRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by: Alex Kucherenko
 * Date: 24.11.2017.
 */
//TODO alk - make the class internal
@Singleton
 class RepositoryFacade @Inject constructor(
	private val sdkProvider: SdkProvider,
	private val bitmapRepository: BitmapRepository,
	private val pictureRepository: PictureRepository,
) {
	private val imageProcessor by lazy { sdkProvider.get().imageProcessor() }

	fun isEmpty(): Boolean {
		return pictureRepository.isEmpty()
	}

	fun create(bitmap: Bitmap, contour: SelectedContour): String {
		val origId = bitmapRepository.create(bitmap)
		val id = pictureRepository.create(origId, contour)
		pictureRepository.read(id)?.let { picture -> update(picture, bitmap) }
		return id
	}

	fun create (picture: Picture) = pictureRepository.create(picture)

	fun create (pictures: List<Picture>) = pictureRepository.create(pictures)

	fun read(id: String): Picture? {
		return pictureRepository.read(id)
	}

	fun readAll(): List<Picture> {
		return pictureRepository.readAll()
	}

	fun readOriginalBitmapWithFilters(id: String, filterTypes: Set<FilterType>): Bitmap? {
		return pictureRepository.read(id)?.original?.let { originalPicture ->
			bitmapRepository.read(originalPicture.id)?.let { originalBitmap ->
				originalPicture.applyFilters(imageProcessor, filterTypes, originalBitmap)
			}
		}
	}

	fun readOriginalBitmap(id: String) = pictureRepository.read(id)?.original?.id?.let { bitmapRepository.read(it) }

	fun readModifiedBitmap(id: String) = pictureRepository.read(id)?.modified?.id?.let { bitmapRepository.read(it) }

	fun readModifiedFile(id: String) = pictureRepository.read(id)?.modified?.id?.let { bitmapRepository.readFile(it) }

	fun update(picture: Picture) {
		bitmapRepository.read(picture.original.id)?.let { originalBitmap ->
			update(picture, originalBitmap)
		}
	}

	fun update(picture: Picture, originalBitmap: Bitmap) {
		bitmapRepository.delete(picture.modified.id)
		val filterTypes = setOf(FilterType.COLOR, FilterType.CROP, FilterType.ROTATE)
		picture.original.applyFilters(imageProcessor, filterTypes, originalBitmap)
			?.let { modifiedBitmap -> bitmapRepository.create(modifiedBitmap) }
			?.let { modifiedPictureId ->
				pictureRepository.update(
					picture.copy(modified = ModifiedPicture(modifiedPictureId)))
			}
	}

	fun delete(id: String) {
		pictureRepository.read(id)?.apply {
			bitmapRepository.delete(original.id)
			bitmapRepository.delete(modified.id)
			pictureRepository.delete(id)
		}
	}

	fun release() {
		bitmapRepository.release()
		pictureRepository.release()
	}
}