package com.ionos.player.ui

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ionos.player.model.PlaybackModel
import com.ionos.player.ui.PlayerScreenEvent.ShowFileActions
import com.nextcloud.client.logger.Logger
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
    private val logger: Logger,
) : ViewModel() {

    private val eventChannel = Channel<PlayerScreenEvent>(Channel.BUFFERED)
    val eventFlow: Flow<PlayerScreenEvent> = eventChannel.receiveAsFlow()

    fun onMoreButtonClick() {
        viewModelScope.launch {
            getCurrentOCFile()?.let { file ->
                eventChannel.send(ShowFileActions(file))
            }
        }
    }

    private suspend fun getCurrentOCFile(): OCFile? {
        return playbackModel.state
            .flatMap { it.currentItemState }
            .map { it.file.id }
            .getOrNull()
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

    class Factory @Inject constructor(
        private val viewModelProvider: Provider<PlayerViewModel>,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelProvider.get() as T
        }
    }
}
