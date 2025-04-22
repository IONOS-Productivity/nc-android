package com.ionos.player.di

import androidx.media3.cast.MediaItemConverter
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.ionos.player.HiDriveMultiplePlaybackErrorStrategy
import com.ionos.player.cache.PlayerPathProvider
import com.ionos.player.chromecast.ChromecastTitleFactory
import com.ionos.player.chromecast.ChromecastTitleFactoryImpl
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.media3.HiDrivePlaybackServiceComponentFactory
import com.ionos.player.media3.item.HiDriveMediaIdFactory
import com.ionos.player.media3.item.HiDriveMediaItemConverter
import com.ionos.player.media3.item.HiDriveMediaItemFactory
import com.ionos.player.media3.session.HiDriveMediaSessionCommandManager
import com.ionos.player.media3.session.HiDriveMediaSessionCommandManagerImpl
import com.ionos.player.media3.store.HiDriveSourceInfoStore
import com.ionos.player.player_mode.PlayerMode
import com.ionos.player.media3.PlaybackServiceComponent
import com.ionos.player.media3.item.MediaIdFactory
import com.ionos.player.media3.item.MediaItemFactory
import com.ionos.player.media3.model.PlaybackModel
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
		model: PlaybackModel<PlayerFileInfo, PlayerMode.Mode>,
	): MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode>

	@Singleton
	@Binds
	abstract fun bindSourceInfoStore(
		store: HiDriveSourceInfoStore
	): SourceInfoStore<PlayerFileInfo>

	@Binds
	abstract fun bindChromecastTitleFactory(
		factory: ChromecastTitleFactoryImpl
	): ChromecastTitleFactory

	@Binds
	abstract fun bindMediaItemFactory(
		factory: HiDriveMediaItemFactory
	): MediaItemFactory<PlayerFileInfo>

	@UnstableApi
	@Binds
	abstract fun bindMediaItemConverter(
		converter: HiDriveMediaItemConverter,
	): MediaItemConverter

	@Binds
	@Singleton
	abstract fun bindMediaIdFactory(
		factory: HiDriveMediaIdFactory
	): MediaIdFactory<PlayerFileInfo>

	@UnstableApi
	@Binds
	@Singleton
	abstract fun bindHiDriveMediaSessionCommandManager(
		manager: HiDriveMediaSessionCommandManagerImpl
	): HiDriveMediaSessionCommandManager

	@UnstableApi
	@Binds
	@Singleton
	abstract fun bindPlaybackServiceComponentFactory(
		factory: HiDrivePlaybackServiceComponentFactory
	): PlaybackServiceComponent.Factory

	@Binds
	@Singleton
	abstract fun bindMultiplePlaybackErrorStrategy(
		strategy: HiDriveMultiplePlaybackErrorStrategy,
	): MultiplePlaybackErrorStrategy<PlayerFileInfo, PlayerMode.Mode>
}