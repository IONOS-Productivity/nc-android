package com.strato.hidrive.player.di

import androidx.media3.common.util.UnstableApi
import com.strato.hidrive.player.PlayerMultiplePlaybackSettings
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionCommandManager
import com.strato.hidrive.player.player_mode.PlayerMode
import com.viseven.develop.media3.session.MediaSessionCommandManager
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlaybackSettings
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