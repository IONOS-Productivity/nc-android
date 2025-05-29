/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import android.content.Context
import androidx.media3.session.MediaController
import com.ionos.player.media3.common.MediaItemFactory
import com.ionos.player.media3.controller.MediaControllerFactory
import com.ionos.player.media3.controller.indexOfFirst
import com.ionos.player.media3.controller.setRepeatMode
import com.ionos.player.media3.controller.updateMediaItems
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackFiles
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.PlaybackModelCompositeListener
import com.ionos.player.model.PlaybackSettings
import com.ionos.player.model.VideoViewSetter
import com.ionos.player.model.error_strategy.PlaybackErrorStrategy
import com.ionos.player.model.file_store.PlaybackFileStore
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

class PlaybackModelImpl @Inject constructor(
	private val context: Context,
	private val mediaSessionHolder: MediaSessionHolder,
	private val mediaItemFactory: MediaItemFactory,
	private val playbackFileStore: PlaybackFileStore,
	private val playbackSettings: PlaybackSettings,
	private val playbackErrorStrategy: PlaybackErrorStrategy,
) : PlaybackModel {

	companion object {
		private const val CHECK_PROGRESS_INTERVAL = 1000
	}

	private val stateFactory = PlaybackStateFactory(playbackFileStore)
	private val compositeListener = PlaybackModelCompositeListener()

	private val checkProgressPeriodicAction = PeriodicAction(CHECK_PROGRESS_INTERVAL) {
		state.ifPresent(compositeListener::onUpdate)
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
			playbackFileStore.clear()
			checkProgressPeriodicAction.stop()
			state.ifPresent(compositeListener::onUpdate)
		}
	}

	private val controllerFactory = MediaControllerFactory(controllerListener)
	private var controllerScope: CoroutineScope? = null
	private var controller: MediaController? = null

    override val state: Optional<PlaybackState> get() {
        return stateFactory.create(controller)
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
					compositeListener.onError(it)
					release()
				}
				.collectLatest { setFiles(it) }
		}
	}

	private fun setFiles(files: PlaybackFiles) {
		if (files.list.isEmpty()) {
			release()
			return
		}

		val currentFile = controller?.currentMediaItem?.let { playbackFileStore.getFile(it.mediaId) }

		playbackFileStore.setFiles(files.list)

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
		mediaSessionHolder.release()
	}

    override fun videoViewSetter(success: (VideoViewSetter) -> Unit) {
		success {
			controller?.setVideoSurfaceHolder(it)
		}
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
		state.ifPresent(compositeListener::onUpdate)
	}

	private fun onPlaybackError(error: Throwable) {
		compositeListener.onError(error)
		state.ifPresent { state ->
			if (playbackErrorStrategy.switchToNextSource(error, state)) {
				playNext()
			}
		}
	}
}
