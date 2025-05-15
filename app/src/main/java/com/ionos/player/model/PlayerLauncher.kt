package com.ionos.player.model

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ionos.player.ui.IonosPlayerActivity
import com.nextcloud.client.logger.Logger
import com.nextcloud.client.preferences.AppPreferences
import com.owncloud.android.MainApp
import com.owncloud.android.datamodel.FileDataStorageManager
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.datamodel.VirtualFolderType
import com.owncloud.android.ui.fragment.SearchType
import com.owncloud.android.utils.FileStorageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PlayerLauncher @Inject constructor(
	private val storageManager: FileDataStorageManager,
	private val preferences: AppPreferences,
	private val playbackModel: PlaybackModel,
	private val logger: Logger,
) {
	private var currentLaunchJob: Job? = null

	fun launch(activity: AppCompatActivity, file: OCFile, searchType: SearchType?) {
		currentLaunchJob?.cancel()
		currentLaunchJob = activity.lifecycleScope.launch {
			try {
				val fileType = file.mimeType.substringBefore("/").lowercase()
				val intent = createIntent(activity, fileType)
				val currentPlaybackFile = file.toPlaybackFile()

				playbackModel.start()
				playbackModel.setFiles(listOf(currentPlaybackFile))
				playbackModel.switchToFile(currentPlaybackFile)
				playbackModel.play()

				activity.startActivity(intent)

				val playbackFiles = getPlaybackFiles(file, fileType, searchType)
				playbackModel.state.ifPresent {
					playbackModel.setFiles(playbackFiles)
				}
			} catch (t: Throwable) {
				if (t is CancellationException) throw t
				logger.e(PlayerLauncher::class.java.simpleName, "Error launching player", t)
			}
		}
	}

	private fun createIntent(context: Context, fileType: String): Intent = when (fileType) {
		"audio" -> IonosPlayerActivity.createAudioPlayerIntent(context)
		"video" -> IonosPlayerActivity.createVideoPlayerIntent(context)
		else -> throw IllegalArgumentException("Unsupported file type: $fileType")
	}

	private suspend fun getPlaybackFiles(file: OCFile, fileType: String, searchType: SearchType?): List<PlaybackFile> {
		return withContext(Dispatchers.IO) {
			when (searchType) {
				SearchType.FAVORITE_SEARCH -> getFavoriteFolderPlaybackFiles(fileType)
				SearchType.GALLERY_SEARCH -> getGalleryFolderPlaybackFiles(fileType)
				SearchType.SHARED_FILTER -> getSharedFolderPlaybackFiles(fileType)
				else -> getRegularFolderPlaybackFiles(file.parentId, fileType)
			}
		}
	}

	private fun getFavoriteFolderPlaybackFiles(fileType: String): List<PlaybackFile> {
		return storageManager.getVirtualFolderContent(VirtualFolderType.FAVORITE, false)
			.filter { it.mimeType.startsWith(fileType, ignoreCase = true) }
			.map { it.toPlaybackFile() }
	}

	private fun getGalleryFolderPlaybackFiles(fileType: String): List<PlaybackFile> {
		return storageManager.allGalleryItems
			.filter { it.mimeType.startsWith(fileType, ignoreCase = true) }
			.let { FileStorageUtils.sortOcFolderDescDateModifiedWithoutFavoritesFirst(it) }
			.map { it.toPlaybackFile() }
	}

	private fun getSharedFolderPlaybackFiles(fileType: String): List<PlaybackFile> {
		return storageManager.shares
			.sortedByDescending { it.sharedDate }
			.distinctBy { it.fileSource }
			.map { it.toPlaybackFile() }
			.filter { it.mimeType.startsWith(fileType, ignoreCase = true) }
	}

	private fun getRegularFolderPlaybackFiles(folderId: Long, fileType: String): List<PlaybackFile> {
		val folder = storageManager.getFileById(folderId) ?: throw IllegalStateException("Folder $folderId not found")
		val sortOrder = preferences.getSortOrderByFolder(folder)
		return storageManager.getFolderContent(folder, MainApp.isOnlyOnDevice())
			.filter { it.mimeType.startsWith(fileType, ignoreCase = true) }
			.let { sortOrder.sortCloudFiles(it.toMutableList()) }
			.map { it.toPlaybackFile() }
	}
}
