package com.ionos.player.model

import com.nextcloud.client.preferences.AppPreferences
import com.owncloud.android.MainApp
import com.owncloud.android.datamodel.FileDataStorageManager
import com.owncloud.android.datamodel.VirtualFolderType
import com.owncloud.android.ui.fragment.SearchType
import com.owncloud.android.utils.FileStorageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaybackFilesRepository @Inject constructor(
	private val storageManager: FileDataStorageManager,
	private val preferences: AppPreferences,
) {

	suspend fun load(folderId: Long, fileType: PlaybackFileType, searchType: SearchType?): List<PlaybackFile> {
		return withContext(Dispatchers.IO) {
			when (searchType) {
				SearchType.FAVORITE_SEARCH -> getFavoriteFolderPlaybackFiles(fileType)
				SearchType.GALLERY_SEARCH -> getGalleryFolderPlaybackFiles(fileType)
				SearchType.SHARED_FILTER -> getSharedFolderPlaybackFiles(fileType)
				else -> getRegularFolderPlaybackFiles(folderId, fileType)
			}
		}
	}

	private fun getFavoriteFolderPlaybackFiles(fileType: PlaybackFileType): List<PlaybackFile> {
		return storageManager.getVirtualFolderContent(VirtualFolderType.FAVORITE, false)
			.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
			.map { it.toPlaybackFile() }
	}

	private fun getGalleryFolderPlaybackFiles(fileType: PlaybackFileType): List<PlaybackFile> {
		return storageManager.allGalleryItems
			.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
			.let { FileStorageUtils.sortOcFolderDescDateModifiedWithoutFavoritesFirst(it) }
			.map { it.toPlaybackFile() }
	}

	private fun getSharedFolderPlaybackFiles(fileType: PlaybackFileType): List<PlaybackFile> {
		return storageManager.shares
			.sortedByDescending { it.sharedDate }
			.distinctBy { it.fileSource }
			.map { it.toPlaybackFile() }
			.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
	}

	private fun getRegularFolderPlaybackFiles(folderId: Long, fileType: PlaybackFileType): List<PlaybackFile> {
		val folder = storageManager.getFileById(folderId) ?: throw IllegalStateException("Folder $folderId not found")
		val sortOrder = preferences.getSortOrderByFolder(folder)
		return storageManager.getFolderContent(folder, MainApp.isOnlyOnDevice())
			.filter { it.mimeType.startsWith(fileType.value, ignoreCase = true) }
			.let { sortOrder.sortCloudFiles(it.toMutableList()) }
			.map { it.toPlaybackFile() }
	}
}
