/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ionos.player.model.PlaybackModel
import com.ionos.player.ui.PlayerScreenEvent.LaunchOpenFileIntent
import com.ionos.player.ui.PlayerScreenEvent.LaunchStreamFileIntent
import com.ionos.player.ui.PlayerScreenEvent.ShowFileActions
import com.ionos.player.ui.PlayerScreenEvent.ShowFileDetails
import com.ionos.player.ui.PlayerScreenEvent.ShowFileExportStartedMessage
import com.ionos.player.ui.PlayerScreenEvent.ShowRemoveFileDialog
import com.ionos.player.ui.PlayerScreenEvent.ShowShareFileDialog
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.jobs.BackgroundJobManager
import com.nextcloud.client.jobs.download.FileDownloadHelper
import com.nextcloud.client.logger.Logger
import com.owncloud.android.R
import com.owncloud.android.datamodel.FileDataStorageManager
import com.owncloud.android.datamodel.OCFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.optionals.getOrNull

class PlayerViewModel @Inject constructor(
    private val playbackModel: PlaybackModel,
    private val storageManager: FileDataStorageManager,
    private val userAccountManager: UserAccountManager,
    private val backgroundJobManager: BackgroundJobManager,
    private val logger: Logger,
) : ViewModel() {

    private val eventChannel = Channel<PlayerScreenEvent>(Channel.BUFFERED)
    val eventFlow: Flow<PlayerScreenEvent> = eventChannel.receiveAsFlow()

    fun onMoreButtonClick() {
        viewModelScope.launch {
            val file = getCurrentOCFile() ?: return@launch
            val actionIds = listOf(
                R.id.action_see_details,
                R.id.action_download_file,
                R.id.action_export_file,
                R.id.action_send_share_file,
                R.id.action_remove_file,
                R.id.action_open_file_with,
                R.id.action_stream_media,
            )
            eventChannel.trySend(ShowFileActions(file, actionIds))
        }
    }

    fun onFileActionChosen(file: OCFile, actionId: Int) {
        when (actionId) {
            R.id.action_see_details -> eventChannel.trySend(ShowFileDetails(file))
            R.id.action_download_file -> startFileDownloading(file)
            R.id.action_export_file -> startFileExport(file)
            R.id.action_send_share_file -> eventChannel.trySend(ShowShareFileDialog(file))
            R.id.action_remove_file -> eventChannel.trySend(ShowRemoveFileDialog(file))
            R.id.action_open_file_with -> onOpenFileWithClick(file)
            R.id.action_stream_media -> onStreamFileClick(file)
        }
    }

    private suspend fun getCurrentOCFile(): OCFile? {
        val currentFileId = playbackModel.state.getOrNull()?.currentItemState?.file?.id
        return currentFileId
            ?.takeIf { it.isDigitsOnly() }
            ?.let { getOCFile(it.toLong()) }
    }

    private suspend fun getOCFile(localId: Long): OCFile? = withContext(Dispatchers.IO) {
        try {
            storageManager.getFileByLocalId(localId)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            logger.e(PlayerViewModel::class.java.simpleName, "Failed to get file by localId: $localId", e)
            null
        }
    }

    private fun startFileDownloading(file: OCFile) {
        val user = userAccountManager.user
        FileDownloadHelper.instance().downloadFileIfNotStartedBefore(user, file)
    }

    private fun startFileExport(file: OCFile) {
        backgroundJobManager.startImmediateFilesExportJob(listOf(file))
        eventChannel.trySend(ShowFileExportStartedMessage)
    }

    private fun onOpenFileWithClick(file: OCFile) {
        playbackModel.pause()
        eventChannel.trySend(LaunchOpenFileIntent(file))
    }

    private fun onStreamFileClick(file: OCFile) {
        playbackModel.pause()
        eventChannel.trySend(LaunchStreamFileIntent(file))
    }

    class Factory @Inject constructor(
        private val viewModelProvider: Provider<PlayerViewModel>,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelProvider.get() as T
        }
    }
}
