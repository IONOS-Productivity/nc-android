package com.ionos.player.di

import com.ionos.player.PlayerMultiplePlaybackSettings
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlaybackSettings
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