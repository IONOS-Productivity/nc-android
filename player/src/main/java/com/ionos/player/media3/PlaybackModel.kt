/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import android.content.Context
import androidx.media3.session.MediaController
import com.annimon.stream.Optional
import com.ionos.player.media3.common.MediaIdFactory
import com.ionos.player.media3.common.MediaItemFactory
import com.ionos.player.media3.controller.MediaControllerFactory
import com.ionos.player.media3.controller.MediaControllerProvider
import com.ionos.player.media3.controller.indexOfFirst
import com.ionos.player.media3.controller.setRepeatMode
import com.ionos.player.media3.controller.updateMediaItems
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.model.CompositeListener
import com.ionos.player.model.MultiplePlaybackSettings
import com.ionos.player.model.MultiplePlayer
import com.ionos.player.model.VideoViewSetter
import com.ionos.player.model.error_strategy.MultiplePlaybackErrorStrategy
import com.ionos.player.model.release_strategy.SourceInfoReleaseStrategy
import com.ionos.player.model.state.MultiplePlaybackState
import com.ionos.player.model.store.SourceInfoStore
import com.ionos.player.model.volume.IVolumeController
import com.ionos.player.model.volume.VolumeController
import com.ionos.player.util.Action
import com.ionos.player.util.ParamAction
import com.ionos.player.util.PeriodicAction
import javax.inject.Inject

class PlaybackModel<SourceInfo> @Inject constructor(
	private val context: Context,
	private val mediaSessionHolder: MediaSessionHolder,
	private val mediaIdFactory: MediaIdFactory<SourceInfo>,
	private val mediaItemFactory: MediaItemFactory<SourceInfo>,
	private val sourceInfoStore: SourceInfoStore<SourceInfo>,
	private val playbackSettings: MultiplePlaybackSettings,
	private val playbackErrorStrategy: MultiplePlaybackErrorStrategy<SourceInfo>,
) : MultiplePlayer.Model<SourceInfo>,
	IVolumeController by VolumeController(context) {

	companion object {
		private const val CHECK_PROGRESS_INTERVAL = 1000
	}

	private val stateFactory = PlaybackStateFactory(sourceInfoStore, playbackSettings)
	private val compositeListener = CompositeListener<SourceInfo>()

	private val checkProgressPeriodicAction = PeriodicAction(CHECK_PROGRESS_INTERVAL) {
		state.ifPresent(compositeListener::onUpdate)
	}

	private val playerListener = PlayerListener(
		checkProgressPeriodicAction,
		this::onPlaybackUpdate,
		this::onPlaybackError,
	)

	private val controllerListener = object : MediaController.Listener {
		override fun onDisconnected(controller: MediaController) {
			controller.removeListener(playerListener)
			sourceInfoStore.clear()
			checkProgressPeriodicAction.stop()
			state.ifPresent(compositeListener::onUpdate)
		}
	}

	private val controllerFactory = MediaControllerFactory(controllerListener)
	private val controllerProvider = MediaControllerProvider(controllerFactory)
	private val controller: MediaController? by controllerProvider

	override fun start(onSuccess: Action, onError: ParamAction<Throwable>) {
		if (controllerProvider.isInitialized) {
			onSuccess.execute()

		} else if (controllerProvider.isInitializing) {
			controllerProvider.addInitializeListener { result ->
				result.onSuccess { onSuccess.execute() }
				result.onFailure { onError.execute(it) }
			}

		} else {
			controllerProvider.initialize(context) { result ->
				result.onSuccess {
					it.addListener(playerListener)
					it.setRepeatMode(playbackSettings.repeatMode)
					it.shuffleModeEnabled = playbackSettings.isShuffle
					onSuccess.execute()
				}
				result.onFailure { onError.execute(it) }
			}
		}
	}

	override fun setSourceInfos(
		sourceInfos: List<SourceInfo>,
		releaseStrategy: SourceInfoReleaseStrategy<SourceInfo>,
	) {
		val releaseCurrentPlayback = controller
			?.currentMediaItem
			?.let { sourceInfoStore.getSourceInfo(it.mediaId) }
			?.let { releaseStrategy.releaseCurrentPlayback(sourceInfos, it) }
			?: true

		sourceInfoStore.setSourceInfos(sourceInfos)

		controller?.let {
			val mediaItems = sourceInfos.map(mediaItemFactory::create)
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

	override fun getState(): Optional<MultiplePlaybackState<SourceInfo>> {
		return stateFactory.create(controller)
	}

	override fun videoViewSetter(success: ParamAction<VideoViewSetter>) {
		success.execute {
			controller?.setVideoSurfaceHolder(it)
		}
	}

	override fun addListener(listener: MultiplePlayer.Model.Listener<SourceInfo>) {
		compositeListener.addListener(listener)
	}

	override fun removeListener(listener: MultiplePlayer.Model.Listener<SourceInfo>) {
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

	override fun repeatSingle() {
		playbackSettings.repeatSingle()
		controller?.setRepeatMode(playbackSettings.repeatMode)
	}

	override fun doNotRepeatSingle() {
		playbackSettings.doNotRepeatSingle()
		controller?.setRepeatMode(playbackSettings.repeatMode)
	}

	override fun shuffle() {
		playbackSettings.shuffle()
		controller?.shuffleModeEnabled = true
	}

	override fun doNotShuffle() {
		playbackSettings.doNotShuffle()
		controller?.shuffleModeEnabled = false
	}

	override fun switchToSourceInfo(sourceInfo: SourceInfo) {
		controller?.run {
			val mediaId = mediaIdFactory.create(sourceInfo)
			val mediaItemIndex = indexOfFirst { it.mediaId == mediaId }
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
