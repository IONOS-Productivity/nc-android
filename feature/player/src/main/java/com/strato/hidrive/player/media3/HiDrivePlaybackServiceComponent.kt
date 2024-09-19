package com.strato.hidrive.player.media3

import android.content.Context
import androidx.media3.common.util.BitmapLoader
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import com.strato.hidrive.player.R
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.image_loading.PlayerImageLoader
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionActivityFactory
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionBitmapLoader
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionCallback
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionCommandManager
import com.strato.hidrive.player.player_mode.PlayerMode
import com.strato.hidrive.player.predicate.IsVideoPredicate
import com.strato.hidrive.player.transformation.FileInfoToIntentTransformation
import com.viseven.develop.media3.PlaybackServiceComponent
import com.viseven.develop.media3.session.MediaSessionActivityFactory
import com.viseven.develop.media3.store.SourceInfoStore
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlaybackSettings

@UnstableApi
class HiDrivePlaybackServiceComponent(
	private val context: Context,
	private val playerFactory: PlayerFactory,
	private val sessionCommandManager: HiDriveMediaSessionCommandManager,
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val playbackSettings: MultiplePlaybackSettings<PlayerMode.Mode>,
	private val imageLoader: PlayerImageLoader,
	private val fileInfoToIntentTransformation: FileInfoToIntentTransformation,
	private val isVideoPredicate: IsVideoPredicate,
) : PlaybackServiceComponent {

	override fun provideMediaSession(): MediaSession {
		return MediaSession
			.Builder(context, playerFactory.create(playbackSettings.getMode()))
			.setBitmapLoader(provideBitmapLoader())
			.setCallback(HiDriveMediaSessionCallback(sessionCommandManager))
			.setCustomLayout(provideCustomLayout())
			.build()
	}

	private fun provideCustomLayout(): List<CommandButton> {
		return listOf(
			CommandButton
				.Builder()
				.setDisplayName(context.getString(R.string.player_media_controls_close_action_title))
				.setIconResId(R.drawable.ic_player_close_white_24dp)
				.setSessionCommand(sessionCommandManager.closeCommand)
				.build(),
		)
	}

	private fun provideBitmapLoader(): BitmapLoader {
		return HiDriveMediaSessionBitmapLoader(
			context,
			sourceInfoStore,
			imageLoader,
			isVideoPredicate,
		)
	}

	override fun provideMediaSessionActivityFactory(): MediaSessionActivityFactory {
		return HiDriveMediaSessionActivityFactory(
			context,
			sourceInfoStore,
			fileInfoToIntentTransformation,
		)
	}

}
