package com.strato.hidrive.domain.filemanager.sorting.comparators

import com.strato.hidrive.domain.entity.RemoteFileInfo
import java.util.Locale

/**
 * User: Dima Muravyov
 * Date: 22.05.2018
 */
class GalleryInfoFileCategoryComparator : Comparator<RemoteFileInfo> {

	companion object {
		private const val EXTENSION_SEPARATOR = '.'
	}

	private enum class FileCategory(val extensions: List<String> = emptyList()) {
		DIRECTORY,
		PICTURES(listOf(
				"jpeg", "svg", "dib", "jpg", "tif", "gif", "png", "bmp", "psd",
				"cdr", "eps", "raw", "nef", "ai", "arw", "cr2", "crw", "dng",
				"jng", "mng", "mrw", "nrw", "orf", "pef", "psb", "raf", "tiff")),
		VIDEO(listOf("avi2", "rmvb", "avi", "mov", "mp4", "wmv", "mkv", "mpg", "3gp", "qt", "rm", "rv")),
		AUDIO(listOf("aac2", "midi", "aiff", "flac", "aac", "ac3", "ape", "ogg", "mid", "ram", "mp3", "wav", "m4a", "aif")),
		DOCUMENTS(listOf("txt2", "docx", "sxw", "txt", "fon", "ttf", "chm", "doc", "pgs", "rtf", "odt", "pdf")),
		OTHER,
		WITHOUT_EXTENSION
	}

	override fun compare(lhs: RemoteFileInfo, rhs: RemoteFileInfo): Int {
		val lhsCategoryIndex = getCategoryIndex(lhs)
		val rhsCategoryIndex = getCategoryIndex(rhs)

		return when {
			lhsCategoryIndex > rhsCategoryIndex -> 1
			lhsCategoryIndex < rhsCategoryIndex -> -1
			else -> 0
		}
	}

	private fun getCategoryIndex(galleryInfo: RemoteFileInfo): Int =
			when {
				galleryInfo.isDirectory -> FileCategory.DIRECTORY.ordinal
				getExtension(galleryInfo).isNotBlank() -> {
					val extension = getExtension(galleryInfo).lowercase(Locale.getDefault())
					var categoryIndex: Int = FileCategory.OTHER.ordinal

					FileCategory.values().forEach { type ->
						if (type.extensions.contains(extension)) {
							categoryIndex = type.ordinal
							return@forEach
						}
					}

					categoryIndex
				}
				else -> FileCategory.WITHOUT_EXTENSION.ordinal
			}

	private fun getExtension(galleryInfo: RemoteFileInfo): String {
		val separatorIndex = galleryInfo.name.lastIndexOf(EXTENSION_SEPARATOR)
		return if (separatorIndex >= 0) {
			galleryInfo.name.substring(separatorIndex + 1)
		} else ""
	}
}