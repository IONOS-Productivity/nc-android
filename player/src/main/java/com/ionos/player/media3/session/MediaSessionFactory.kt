package com.ionos.player.media3.session

import android.content.Context
import android.os.Bundle
import androidx.media3.common.util.BitmapLoader
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.ionos.player.R
import com.ionos.player.media3.common.PlayerFactory
import com.ionos.player.model.image_loader.PlayerImageLoader
import com.ionos.player.model.predicate.IsVideoPredicate
import com.ionos.player.model.store.SourceInfoStore
import javax.inject.Inject

@UnstableApi
class MediaSessionFactory @Inject constructor(
	private val context: Context,
	private val playerFactory: PlayerFactory,
	private val sessionCallback: MediaSessionCallback,
	private val sourceInfoStore: SourceInfoStore,
	private val imageLoader: PlayerImageLoader,
	private val isVideoPredicate: IsVideoPredicate,
) {

	fun create(): MediaSession {
		return MediaSession
			.Builder(context, playerFactory.create())
			.setBitmapLoader(provideBitmapLoader())
			.setCallback(sessionCallback)
			.setCustomLayout(provideCustomLayout())
			.build()
	}

	private fun provideCustomLayout(): List<CommandButton> {
		return listOf(
			CommandButton
				.Builder()
				.setDisplayName(context.getString(R.string.player_media_controls_close_action_title))
				.setIconResId(R.drawable.ic_player_close_white_24dp)
				.setSessionCommand(SessionCommand(MediaSessionCallback.CLOSE_ACTION, Bundle.EMPTY))
				.build(),
		)
	}

	private fun provideBitmapLoader(): BitmapLoader {
		return MediaSessionBitmapLoader(
			context,
			sourceInfoStore,
			imageLoader,
			isVideoPredicate,
		)
	}
}
