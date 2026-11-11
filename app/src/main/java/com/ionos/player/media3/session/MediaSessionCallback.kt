/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.session

import android.os.Bundle
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.ionos.player.media3.common.MediaItemFactory
import com.ionos.player.media3.resumption.PlaybackResumptionConfigStore
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackFilesRepository
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.getPlaybackUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.guava.future
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Provider

class MediaSessionCallback @Inject constructor(
    private val playbackResumptionConfigStore: PlaybackResumptionConfigStore,
    private val playbackFilesRepository: PlaybackFilesRepository,
    private val mediaItemFactory: MediaItemFactory,
    private val playbackModelProvider: Provider<PlaybackModel>,
) : MediaSession.Callback {
    private val playbackModel get() = playbackModelProvider.get()

    companion object {
        const val CLOSE_ACTION = "CLOSE_ACTION"
    }

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): ConnectionResult {
        val connectionResult = super.onConnect(session, controller)
        val sessionCommandsBuilder = connectionResult.availableSessionCommands.buildUpon()
        sessionCommandsBuilder.add(SessionCommand(CLOSE_ACTION, Bundle.EMPTY))
        val sessionCommands = sessionCommandsBuilder.build()
        return ConnectionResult.accept(sessionCommands, connectionResult.availablePlayerCommands)
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        if (customCommand.customAction == CLOSE_ACTION) {
            playbackModel.release()
        }
        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
    }

    @UnstableApi
    override fun onPlaybackResumption(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo
    ): ListenableFuture<MediaItemsWithStartPosition> {
        return GlobalScope.future {
            try {
                val (currentFileId, folderId, fileType, searchType) = playbackResumptionConfigStore.loadConfig()
                    ?: throw IllegalStateException("Playback resumption config is null")
                val playbackFilesFlow = playbackFilesRepository.observe(folderId, fileType, searchType)
                val playbackFiles = playbackFilesFlow.first().list.ifEmpty {
                    throw IllegalStateException("Playback files are empty")
                }
                withContext(Dispatchers.Main) {
                    playbackModel.start()
                    playbackModel.setFilesFlow(playbackFilesFlow.drop(1))
                }
                playbackFiles.toMediaItemsWithStartPosition(currentFileId)
            } catch (t: Throwable) {
                if (t is CancellationException) throw t
                val stubPlaybackFile = getStubPlaybackFile()
                val stubPlaybackFiles = listOf(stubPlaybackFile)
                withContext(Dispatchers.Main) {
                    playbackModel.start()
                }
                stubPlaybackFiles.toMediaItemsWithStartPosition(stubPlaybackFile.id)
            }
        }
    }

    @UnstableApi
    private fun List<PlaybackFile>.toMediaItemsWithStartPosition(currentFileId: String) = MediaItemsWithStartPosition(
        map { mediaItemFactory.create(it) },
        indexOfFirst { it.id == currentFileId },
        0,
    )

    /**
     * Workaround to avoid internal media3 crash
     */
    private fun getStubPlaybackFile() = PlaybackFile(
        id = "0",
        uri = getPlaybackUri(0L).toString(),
        name = "",
        mimeType = "audio/mpeg",
        contentLength = 0L,
        lastModified = 0L,
        isFavorite = false,
    )
}
