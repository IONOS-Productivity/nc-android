package com.ionos.player.model

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import com.ionos.player.util.observeContentChanges
import com.nextcloud.client.preferences.AppPreferences
import com.owncloud.android.MainApp
import com.owncloud.android.datamodel.FileDataStorageManager
import com.owncloud.android.datamodel.VirtualFolderType
import com.owncloud.android.db.ProviderMeta.ProviderTableMeta
import com.owncloud.android.ui.fragment.SearchType
import com.owncloud.android.utils.FileSortOrder
import com.owncloud.android.utils.FileStorageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaybackFilesRepository @Inject constructor(
	private val context: Context,
	private val storageManager: FileDataStorageManager,
	private val preferences: AppPreferences,
) {
	companion object {
		private const val FETCH_DATA_DEBOUNCE_MS = 250L
	}

	fun observe(folderId: Long, fileType: PlaybackFileType, searchType: SearchType?): Flow<List<PlaybackFile>> {
		return when (searchType) {
			SearchType.FAVORITE_SEARCH -> observeFavoritePlaybackFiles(fileType)
			SearchType.GALLERY_SEARCH -> observeGalleryPlaybackFiles(fileType)
			SearchType.SHARED_FILTER -> observeSharedPlaybackFiles(fileType)
			else -> observeFolderPlaybackFiles(folderId, fileType, MainApp.isOnlyOnDevice())
		}
	}

	suspend fun get(folderId: Long, fileType: PlaybackFileType, searchType: SearchType?): List<PlaybackFile> {
		return when (searchType) {
			SearchType.FAVORITE_SEARCH -> getFavoritePlaybackFiles(fileType)
			SearchType.GALLERY_SEARCH -> getGalleryPlaybackFiles(fileType)
			SearchType.SHARED_FILTER -> getSharedPlaybackFiles(fileType)
			else -> getFolderPlaybackFiles(folderId, fileType, MainApp.isOnlyOnDevice())
		}
	}

	private fun observeFavoritePlaybackFiles(fileType: PlaybackFileType): Flow<List<PlaybackFile>> {
		val uri = ProviderTableMeta.CONTENT_URI_VIRTUAL
		return observeData(uri, false) {
			getFavoritePlaybackFiles(fileType)
		}
	}

	private suspend fun getFavoritePlaybackFiles(fileType: PlaybackFileType): List<PlaybackFile> {
		return withContext(Dispatchers.IO) {
			storageManager.getVirtualFolderContent(VirtualFolderType.FAVORITE, false)
				.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
				.map { it.toPlaybackFile() }
		}
	}

	private fun observeGalleryPlaybackFiles(fileType: PlaybackFileType): Flow<List<PlaybackFile>> {
		val uri = ProviderTableMeta.CONTENT_URI
		return observeData(uri, true) {
			getGalleryPlaybackFiles(fileType)
		}
	}

	private suspend fun getGalleryPlaybackFiles(fileType: PlaybackFileType): List<PlaybackFile> {
		return withContext(Dispatchers.IO) {
			storageManager.allGalleryItems
				.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
				.let { FileStorageUtils.sortOcFolderDescDateModifiedWithoutFavoritesFirst(it) }
				.map { it.toPlaybackFile() }
		}
	}

	private fun observeSharedPlaybackFiles(fileType: PlaybackFileType): Flow<List<PlaybackFile>> {
		val uri = ProviderTableMeta.CONTENT_URI_SHARE
		return observeData(uri, false) {
			getSharedPlaybackFiles(fileType)
		}
	}

	private suspend fun getSharedPlaybackFiles(fileType: PlaybackFileType): List<PlaybackFile> {
		return withContext(Dispatchers.IO) {
			storageManager.shares
				.sortedByDescending { it.sharedDate }
				.distinctBy { it.fileSource }
				.map { it.toPlaybackFile() }
				.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
		}
	}

	private fun observeFolderPlaybackFiles(
		folderId: Long,
		fileType: PlaybackFileType,
		onDeviceOnly: Boolean,
	): Flow<List<PlaybackFile>> {
		val uri = ContentUris.withAppendedId(ProviderTableMeta.CONTENT_URI_DIR, folderId)
		val sortOrderFlow = flow {
			emit(getFolderSortOrder(folderId))
		}
		return sortOrderFlow.flatMapConcat { sortOrder ->
			observeData(uri, false) {
				getFolderPlaybackFiles(folderId, fileType, onDeviceOnly, sortOrder)
			}
		}
	}

	private suspend fun getFolderPlaybackFiles(
		folderId: Long,
		fileType: PlaybackFileType,
		onDeviceOnly: Boolean,
		sortOrder: FileSortOrder? = null,
	): List<PlaybackFile> {
		return withContext(Dispatchers.IO) {
			val folder = storageManager.getFileById(folderId) ?: throw IllegalStateException("Folder not found")
			val sortOrder = sortOrder ?: preferences.getSortOrderByFolder(folder)
			storageManager.getFolderContent(folder, onDeviceOnly)
				.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
				.let { sortOrder.sortCloudFiles(it.toMutableList()) }
				.map { it.toPlaybackFile() }
		}
	}

	private suspend fun getFolderSortOrder(folderId: Long): FileSortOrder {
		return withContext(Dispatchers.IO) {
			val folder = storageManager.getFileById(folderId) ?: throw IllegalStateException("Folder not found")
			preferences.getSortOrderByFolder(folder)
		}
	}

	private fun <T> observeData(uri: Uri, notifyForDescendants: Boolean, fetchData: suspend () -> T): Flow<T> {
		return context.contentResolver.observeContentChanges(uri, notifyForDescendants)
			.debounce(FETCH_DATA_DEBOUNCE_MS) // Debounce to avoid too frequent data fetching for batch updates
			.map { fetchData() }
			.onStart { emit(fetchData()) }
			.distinctUntilChanged()
	}
}
