package com.ionos.player.di

import androidx.media3.common.util.UnstableApi
import com.ionos.player.PlayerMultiplePlaybackSettings
import com.ionos.player.media3.session.HiDriveMediaSessionCommandManager
import com.ionos.player.player_mode.PlayerMode
import com.ionos.player.media3.session.MediaSessionCommandManager
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlaybackSettings
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PlayerCoreModule {

	@UnstableApi
	@Binds
	@Singleton
	abstract fun bindMediaSessionCommandManager(
		manager: HiDriveMediaSessionCommandManager
	): MediaSessionCommandManager<PlayerMode.Mode>

	@Binds
	@Singleton
	abstract fun provideHiDriveMultiplePlaybackSettings(
		playbackSettings: PlayerMultiplePlaybackSettings,
	): MultiplePlaybackSettings<PlayerMode.Mode>

}