package com.ionos.player

import com.ionos.player.model.MultiplePlaybackSettings
import com.ionos.player.model.PlayerMultiplePlaybackSettings
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PlayerCoreModule {

	@Binds
	@Singleton
	abstract fun provideHiDriveMultiplePlaybackSettings(
		playbackSettings: PlayerMultiplePlaybackSettings,
	): MultiplePlaybackSettings

}