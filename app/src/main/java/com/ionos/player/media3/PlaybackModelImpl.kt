/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import android.content.Context
import android.view.SurfaceView
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import com.ionos.player.media3.common.MediaItemFactory
import com.ionos.player.media3.common.playbackFile
import com.ionos.player.media3.controller.MediaControllerFactory
import com.ionos.player.media3.controller.indexOfFirst
import com.ionos.player.media3.controller.setRepeatMode
import com.ionos.player.media3.controller.updateMediaItems
import com.ionos.player.media3.session.MediaSessionFactory
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackFiles
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.PlaybackModelCompositeListener
import com.ionos.player.model.PlaybackSettings
import com.ionos.player.model.error_strategy.PlaybackErrorStrategy
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.RepeatMode
import com.ionos.player.util.PeriodicAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Optional
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaybackModelImpl @Inject constructor(
    private val context: Context,
    private val mediaSessionFactory: MediaSessionFactory,
    private val mediaItemFactory: MediaItemFactory,
    private val playbackSettings: PlaybackSettings,
    private val playbackErrorStrategy: PlaybackErrorStrategy,
) : PlaybackModel, MediaSessionHolder {

    companion object {
        private const val CHECK_PROGRESS_INTERVAL = 1000
    }

    private val stateFactory = PlaybackStateFactory()
    private val compositeListener = PlaybackModelCompositeListener()

    private val checkProgressPeriodicAction = PeriodicAction(CHECK_PROGRESS_INTERVAL) {
        state.ifPresent(compositeListener::onPlaybackUpdate)
    }

    private val playerListener = PlaybackModelPlayerListener(
        checkProgressPeriodicAction,
        this::onPlaybackUpdate,
        this::onPlaybackError,
    )

    private val controllerListener = object : MediaController.Listener {
        override fun onDisconnected(controller: MediaController) {
            controller.removeListener(playerListener)
            controllerScope?.cancel()
            checkProgressPeriodicAction.stop()
            state.ifPresent(compositeListener::onPlaybackUpdate)
        }
    }

    private val controllerFactory = MediaControllerFactory(controllerListener)
    private var controllerScope: CoroutineScope? = null
    private var controller: MediaController? = null

    private var mediaSession: MediaSession? = null

    override val state: Optional<PlaybackState>
        get() {
            return stateFactory.create(controller)
        }

    @UnstableApi
    override fun getMediaSession(): MediaSession {
        return mediaSession ?: mediaSessionFactory.create().also {
            mediaSession = it
        }
    }

    override suspend fun start() {
        controller = controllerFactory.create(context).apply {
            addListener(playerListener)
            setRepeatMode(playbackSettings.repeatMode)
            shuffleModeEnabled = playbackSettings.isShuffle
            controllerScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        }
    }

    override fun setFilesFlow(filesFlow: Flow<PlaybackFiles>) {
        controllerScope?.launch {
            filesFlow
                .catch {
                    compositeListener.onPlaybackError(it)
                    release()
                }
                .collectLatest { setFiles(it) }
        }
    }

    override fun setFiles(files: PlaybackFiles) {
        if (files.list.isEmpty()) {
            release()
            return
        }

        val currentFile = controller?.currentMediaItem?.mediaMetadata?.playbackFile

        controller?.let { controller ->
            val mediaItems = files.list.map(mediaItemFactory::create)

            if (currentFile == null) {
                controller.setMediaItems(mediaItems)
            } else if (files.list.any { it.id == currentFile.id }) {
                controller.updateMediaItems(mediaItems)
            } else {
                val nextFileIndex = (files.list + currentFile)
                    .sortedWith(files.comparator)
                    .indexOfFirst { it.id == currentFile.id }
                    .let { if (it in 0..files.list.lastIndex) it else 0 }
                controller.setMediaItems(mediaItems, nextFileIndex, 0)
            }

            controller.prepare()
        }
    }

    override fun release() {
        controller?.release()
        mediaSession?.player?.release()
        mediaSession?.release()
        mediaSession = null
    }

    override fun setVideoSurfaceView(surfaceView: SurfaceView?) {
        controller?.setVideoSurfaceView(surfaceView)
    }

    override fun addListener(listener: PlaybackModel.Listener) {
        compositeListener.addListener(listener)
    }

    override fun removeListener(listener: PlaybackModel.Listener) {
        compositeListener.removeListener(listener)
    }

    override fun play() {
        controller?.run {
            prepare()
            play()
        }
    }

    override fun pause() {
        controller?.pause()
    }

    override fun stop() {
        controller?.stop()
    }

    override fun playNext() {
        controller?.run {
            seekToNextMediaItem()
            prepare()
        }
    }

    override fun playPrevious() {
        controller?.run {
            seekToPreviousMediaItem()
            prepare()
        }
    }

    override fun seekToPosition(positionInMilliseconds: Int) {
        controller?.seekTo(positionInMilliseconds.toLong())
    }

    override fun setRepeatMode(repeatMode: RepeatMode) {
        playbackSettings.setRepeatMode(repeatMode)
        controller?.setRepeatMode(repeatMode)
    }

    override fun setShuffle(shuffle: Boolean) {
        playbackSettings.setShuffle(shuffle)
        controller?.shuffleModeEnabled = shuffle
    }

    override fun switchToFile(file: PlaybackFile) {
        controller?.run {
            val mediaItemIndex = indexOfFirst { it.mediaId == file.id }
            if (mediaItemIndex >= 0 && mediaItemIndex != currentMediaItemIndex) {
                seekToDefaultPosition(mediaItemIndex)
                prepare()
            }
        }
    }

    private fun onPlaybackUpdate() {
        state.ifPresent(compositeListener::onPlaybackUpdate)
    }

    private fun onPlaybackError(error: Throwable) {
        compositeListener.onPlaybackError(error)
        state.ifPresent { state ->
            if (playbackErrorStrategy.switchToNextSource(error, state)) {
                playNext()
            }
        }
    }
}
