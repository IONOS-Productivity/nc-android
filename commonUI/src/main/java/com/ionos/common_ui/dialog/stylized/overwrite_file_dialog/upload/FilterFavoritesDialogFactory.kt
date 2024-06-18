package com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.upload

import android.content.Context
import com.ionos.common_ui.dialog.optional.Function
import io.reactivex.Maybe

data class FilterFavoritesDialogData<T>(
	val destinationDirPath: String,
	val listForUpload: List<T>,
	val toNameMapping: Function<T, String>,
)

interface FilterFavoritesDialogFactory<T> {

	fun create(
		data: FilterFavoritesDialogData<T>,
		activityContext: Context,
	): FilterFavoritesDialog<T>

}

interface FilterFavoritesDialog<T> {
	fun filter(): Maybe<List<T>>
}