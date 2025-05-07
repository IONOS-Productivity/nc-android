package com.ionos.player

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.ionos.player.media3.PlaybackModel
import com.ionos.player.media3.common.HiDriveMediaIdFactory
import com.ionos.player.media3.common.HiDriveMediaItemFactory
import com.ionos.player.media3.common.MediaIdFactory
import com.ionos.player.media3.common.MediaItemFactory
import com.ionos.player.media3.datasource.PlayerPathProvider
import com.ionos.player.media3.session.HiDriveMediaSessionFactory
import com.ionos.player.media3.session.HiDriveMediaSessionHolder
import com.ionos.player.media3.session.MediaSessionFactory
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.model.MultiplePlayer
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.error_strategy.HiDriveMultiplePlaybackErrorStrategy
import com.ionos.player.model.error_strategy.MultiplePlaybackErrorStrategy
import com.ionos.player.model.store.HiDriveSourceInfoStore
import com.ionos.player.model.store.SourceInfoStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [PlayerCoreModule::class])
abstract class PlayerModule {

	companion object{

		const val PLAYER_CACHE_SIZE_QUALIFIER = "PLAYER_CACHE_SIZE_QUALIFIER"

		@UnstableApi
		@Singleton
		@Provides
		fun provideCache(
			pathProvider: PlayerPathProvider,
			@Named(PLAYER_CACHE_SIZE_QUALIFIER) playerCacheSize: Long,
		): Cache {
			return SimpleCache(
				File(
					pathProvider.getCacheFolderPath(),
					pathProvider.getPlayerCacheFolderName()
				),
				LeastRecentlyUsedCacheEvictor(playerCacheSize)
			)
		}
	}

	@UnstableApi
	@Binds
	@Singleton
	abstract fun bindMultiplePlayerModel(
		model: PlaybackModel<PlayerFileInfo>,
	): MultiplePlayer.Model<PlayerFileInfo>

	@Singleton
	@Binds
	abstract fun bindSourceInfoStore(
		store: HiDriveSourceInfoStore
	): SourceInfoStore<PlayerFileInfo>

	@Binds
	abstract fun bindMediaItemFactory(
		factory: HiDriveMediaItemFactory
	): MediaItemFactory<PlayerFileInfo>

	@Binds
	@Singleton
	abstract fun bindMediaIdFactory(
		factory: HiDriveMediaIdFactory
	): MediaIdFactory<PlayerFileInfo>

	@Binds
	@Singleton
	abstract fun bindMultiplePlaybackErrorStrategy(
		strategy: HiDriveMultiplePlaybackErrorStrategy,
	): MultiplePlaybackErrorStrategy<PlayerFileInfo>

    @UnstableApi
    @Binds
	abstract fun bindMediaSessionFactory(
		factory: HiDriveMediaSessionFactory,
	): MediaSessionFactory

	@Binds
	abstract fun bindMediaSessionHolder(
		holder: HiDriveMediaSessionHolder,
	): MediaSessionHolder
}