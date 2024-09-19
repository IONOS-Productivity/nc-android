package com.strato.hidrive.player.media3

import android.content.Context
import androidx.media3.common.util.UnstableApi
import com.strato.hidrive.player.PlayerMultiplePlaybackSettings
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.image_loading.PlayerImageLoader
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionCommandManager
import com.strato.hidrive.player.predicate.IsVideoPredicate
import com.strato.hidrive.player.transformation.FileInfoToIntentTransformation
import com.viseven.develop.media3.PlaybackServiceComponent
import com.viseven.develop.media3.store.SourceInfoStore
import javax.inject.Inject

@UnstableApi
class HiDrivePlaybackServiceComponentFactory @Inject constructor(
	private val context: Context,
	private val playerFactory: PlayerFactory,
	private val sessionCommandManager: HiDriveMediaSessionCommandManager,
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val playbackSettings: PlayerMultiplePlaybackSettings,
	private val imageLoader: PlayerImageLoader,
	private val fileInfoToIntentTransformation: FileInfoToIntentTransformation,
	private val isVideoPredicate: IsVideoPredicate,
) : PlaybackServiceComponent.Factory {

	override fun create(): PlaybackServiceComponent {
		return HiDrivePlaybackServiceComponent(
			context,
			playerFactory,
			sessionCommandManager,
			sourceInfoStore,
			playbackSettings,
			imageLoader,
			fileInfoToIntentTransformation,
			isVideoPredicate,
		)
	}
}
