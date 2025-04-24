package com.ionos.player.di

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.ionos.player.HiDriveMultiplePlaybackErrorStrategy
import com.ionos.player.cache.PlayerPathProvider
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.media3.HiDriveMediaSessionFactory
import com.ionos.player.media3.item.HiDriveMediaIdFactory
import com.ionos.player.media3.item.HiDriveMediaItemFactory
import com.ionos.player.media3.store.HiDriveSourceInfoStore
import com.ionos.player.media3.item.MediaIdFactory
import com.ionos.player.media3.item.MediaItemFactory
import com.ionos.player.media3.model.PlaybackModel
import com.ionos.player.media3.session.HiDriveMediaSessionActivityFactory
import com.ionos.player.media3.session.HiDriveMediaSessionHolder
import com.ionos.player.media3.session.MediaSessionActivityFactory
import com.ionos.player.media3.session.MediaSessionFactory
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.media3.store.SourceInfoStore
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackErrorStrategy
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer
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

	@Binds
	abstract fun bindMediaSessionActivityFactory(
		factory: HiDriveMediaSessionActivityFactory,
	): MediaSessionActivityFactory
}