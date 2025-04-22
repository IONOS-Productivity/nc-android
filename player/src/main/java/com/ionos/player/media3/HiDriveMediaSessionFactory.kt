package com.ionos.player.media3

import android.content.Context
import android.os.Bundle
import androidx.media3.common.util.BitmapLoader
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.ionos.player.R
import com.ionos.player.chromecast.PlayerChromecastModel
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.image_loading.PlayerImageLoader
import com.ionos.player.media3.session.HiDriveMediaSessionBitmapLoader
import com.ionos.player.media3.session.HiDriveMediaSessionCallback
import com.ionos.player.media3.session.MediaSessionFactory
import com.ionos.player.media3.store.SourceInfoStore
import com.ionos.player.player_mode.PlayerMode
import com.ionos.player.predicate.IsVideoPredicate
import java.util.Optional
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

@UnstableApi
class HiDriveMediaSessionFactory @Inject constructor(
	private val context: Context,
	private val playerFactory: PlayerFactory,
	private val chromecastModel: Optional<PlayerChromecastModel>,
	private val sessionCallback: HiDriveMediaSessionCallback,
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val imageLoader: PlayerImageLoader,
	private val isVideoPredicate: IsVideoPredicate,
) : MediaSessionFactory {
    private val playerMode get() = chromecastModel.getOrNull()?.state()?.getPlayerMode() ?: PlayerMode.Mode.REGULAR

	override fun create(): MediaSession {
		return MediaSession
			.Builder(context, playerFactory.create(playerMode))
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
				.setSessionCommand(SessionCommand(HiDriveMediaSessionCallback.CLOSE_ACTION, Bundle.EMPTY))
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
}
