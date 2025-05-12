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
import com.ionos.player.media3.controller.MediaControllerProvider
import com.ionos.player.media3.controller.indexOfFirst
import com.ionos.player.media3.controller.setRepeatMode
import com.ionos.player.media3.controller.updateMediaItems
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.PlaybackModelCompositeListener
import com.ionos.player.model.PlaybackSettings
import com.ionos.player.model.VideoViewSetter
import com.ionos.player.model.file_store.PlaybackFileStore
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.RepeatMode
import com.ionos.player.model.strategy.error.PlaybackErrorStrategy
import com.ionos.player.model.strategy.release.PlaybackReleaseStrategy
import com.ionos.player.util.PeriodicAction
import java.util.Optional
import javax.inject.Inject

class PlaybackModelImpl @Inject constructor(
	private val context: Context,
	private val mediaSessionHolder: MediaSessionHolder,
	private val mediaItemFactory: MediaItemFactory,
	private val playbackFileStore: PlaybackFileStore,
	private val playbackSettings: PlaybackSettings,
	private val playbackErrorStrategy: PlaybackErrorStrategy,
    private val playbackReleaseStrategy: PlaybackReleaseStrategy,
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
			playbackFileStore.clear()
			checkProgressPeriodicAction.stop()
			state.ifPresent(compositeListener::onUpdate)
		}
	}

	private val controllerFactory = MediaControllerFactory(controllerListener)
	private val controllerProvider = MediaControllerProvider(controllerFactory)
	private val controller: MediaController? by controllerProvider

    override val state: Optional<PlaybackState> get() {
        return stateFactory.create(controller)
    }

    override fun start(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
		if (controllerProvider.isInitialized) {
			onSuccess()

		} else if (controllerProvider.isInitializing) {
			controllerProvider.addInitializeListener { result ->
				result.onSuccess { onSuccess() }
				result.onFailure { onError(it) }
			}

		} else {
			controllerProvider.initialize(context) { result ->
				result.onSuccess {
					it.addListener(playerListener)
					it.setRepeatMode(playbackSettings.repeatMode)
					it.shuffleModeEnabled = playbackSettings.isShuffle
					onSuccess()
				}
				result.onFailure { onError(it) }
			}
		}
	}

	override fun setFiles(files: List<PlaybackFile>) {
		val releaseCurrentPlayback = controller
			?.currentMediaItem
			?.let { playbackFileStore.getFile(it.mediaId) }
			?.let { playbackReleaseStrategy.releaseCurrentPlayback(files, it) }
			?: true

		playbackFileStore.setFiles(files)

		controller?.let {
			val mediaItems = files.map(mediaItemFactory::create)
			if (releaseCurrentPlayback) {
				it.setMediaItems(mediaItems)
			} else {
				it.updateMediaItems(mediaItems)
			}
			it.prepare()
		}
	}

	override fun release() {
		controllerProvider.release()
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
