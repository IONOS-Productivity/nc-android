package com.strato.hidrive.player.di

import androidx.media3.cast.MediaItemConverter
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.strato.hidrive.player.HiDriveMultiplePlaybackErrorStrategy
import com.strato.hidrive.player.cache.PlayerPathProvider
import com.strato.hidrive.player.chromecast.ChromecastTitleFactory
import com.strato.hidrive.player.chromecast.ChromecastTitleFactoryImpl
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.media3.HiDrivePlaybackServiceComponentFactory
import com.strato.hidrive.player.media3.item.HiDriveMediaIdFactory
import com.strato.hidrive.player.media3.item.HiDriveMediaItemConverter
import com.strato.hidrive.player.media3.item.HiDriveMediaItemFactory
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionCommandManager
import com.strato.hidrive.player.media3.session.HiDriveMediaSessionCommandManagerImpl
import com.strato.hidrive.player.media3.store.HiDriveSourceInfoStore
import com.strato.hidrive.player.player_mode.PlayerMode
import com.viseven.develop.media3.PlaybackServiceComponent
import com.viseven.develop.media3.item.MediaIdFactory
import com.viseven.develop.media3.item.MediaItemFactory
import com.viseven.develop.media3.model.PlaybackModel
import com.viseven.develop.media3.store.SourceInfoStore
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackErrorStrategy
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer
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