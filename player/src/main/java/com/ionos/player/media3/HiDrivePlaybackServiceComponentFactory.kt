package com.ionos.player.media3

import android.content.Context
import androidx.media3.common.util.UnstableApi
import com.ionos.player.PlayerMultiplePlaybackSettings
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.image_loading.PlayerImageLoader
import com.ionos.player.media3.session.HiDriveMediaSessionCommandManager
import com.ionos.player.predicate.IsVideoPredicate
import com.ionos.player.transformation.FileInfoToIntentTransformation
import com.ionos.player.media3.store.SourceInfoStore
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
