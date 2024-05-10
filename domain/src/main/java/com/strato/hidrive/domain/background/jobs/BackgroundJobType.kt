package com.strato.hidrive.domain.background.jobs

enum class BackgroundJobType(val id: Int) {
	DOWNLOAD(1),
	FAVORITE(2),
	ZIP_DOWNLOAD(3),
	CAMERA_UPLOAD(4),
	SAFE_UPLOAD(5),
	SYNCHRONIZE_EDITED_FILE(6),
	UPLOAD_WITH_AUTONAME(7),
	UPLOAD_WITH_OVERWRITE(8),
	FILE_PICKER_UPLOAD_WITH_AUTONAME(9),
	FILE_PICKER_FOLDER_UPLOAD(10),
	;

	companion object {
		fun byId(id: Int): BackgroundJobType {
			return values().first { it.id == id }
		}

		@JvmStatic
		fun getUploadTypes(): List<BackgroundJobType> {
			return listOf(
				UPLOAD_WITH_OVERWRITE,
				UPLOAD_WITH_AUTONAME,
				CAMERA_UPLOAD,
				SAFE_UPLOAD,
				SYNCHRONIZE_EDITED_FILE,
				FILE_PICKER_UPLOAD_WITH_AUTONAME,
				FILE_PICKER_FOLDER_UPLOAD,
			)
		}

		@JvmStatic
		fun getDownloadTypes(): List<BackgroundJobType> {
			return listOf(
				DOWNLOAD,
				ZIP_DOWNLOAD
			)
		}

		@JvmStatic
		fun getFilePickerTypes(): List<BackgroundJobType> {
			return listOf(
				FILE_PICKER_UPLOAD_WITH_AUTONAME,
				FILE_PICKER_FOLDER_UPLOAD
			)
		}

		@JvmStatic
		fun getBackgroundTypes(): List<BackgroundJobType> =
			values()
				.toMutableList()
				.apply {
					removeAll(getFilePickerTypes())
				}
	}
}