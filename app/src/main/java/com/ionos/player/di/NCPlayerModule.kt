package com.ionos.player.di

import androidx.media3.common.util.UnstableApi
import com.ionos.player.NCMultiplePlaybackSettings
import com.ionos.player.activity.IonosPlayerActivity
import com.ionos.player.cab.PlayerCABStrategyProviderImpl
import com.ionos.player.cache.InMemoryPlayerSourceInfoCache
import com.ionos.player.cache.PlayerPathProviderImpl
import com.ionos.player.cache.PlayerSourceInfoCache
import com.ionos.player.image_loading.PlayerImageLoaderImpl
import com.ionos.player.message.NCPlayerExceptionMessageProvider
import com.ionos.player.message.PlayerMessageBuilderFactoryImpl
import com.ionos.player.predicate.FileBeingProcessedPredicateImpl
import com.ionos.player.predicate.IsVideoPredicateImpl
import com.ionos.player.transformation.FileInfoToCacheKeyTransformationImpl
import com.ionos.player.transformation.FileInfoToDisplayNameTransformationImpl
import com.ionos.player.transformation.FileInfoToIntentTransformationImpl
import com.ionos.player.transformation.FileInfoToLastModifiedDateTransformationImpl
import com.ionos.player.transformation.FileInfoToMimetypeTransformationImpl
import com.ionos.player.transformation.FileInfoToStringSizeTransformationImpl
import com.ionos.player.transformation.FileInfoToUriTransformationImpl
import com.ionos.player.transformation.MediaItemToDataSourceFactoryTransformationImpl
import com.ionos.player.transformation.PlayerFileInfoToExifInfoProviderTransformationImpl
import com.ionos.player.util.PlayerLocaleProviderImpl
import com.strato.hidrive.player.PlayerMultiplePlaybackSettings
import com.strato.hidrive.player.cache.PlayerPathProvider
import com.strato.hidrive.player.chromecast.PlayerChromecastModel
import com.strato.hidrive.player.di.PlayerExifInfoViewDependencies
import com.strato.hidrive.player.di.PlayerModule
import com.strato.hidrive.player.di.PlayerModule.Companion.PLAYER_CACHE_SIZE_QUALIFIER
import com.strato.hidrive.player.image_loading.PlayerImageLoader
import com.strato.hidrive.player.media3.PlayerFactory
import com.strato.hidrive.player.media3.PlayerFactoryImpl
import com.strato.hidrive.player.media3.player.CreateExoPlayer
import com.strato.hidrive.player.media3.player.CreateStubPlayer
import com.strato.hidrive.player.message.PlayerMessageBuilderFactory
import com.strato.hidrive.player.predicate.FileBeingProcessedPredicate
import com.strato.hidrive.player.predicate.IsVideoPredicate
import com.strato.hidrive.player.transformation.FileInfoToCacheKeyTransformation
import com.strato.hidrive.player.transformation.FileInfoToDisplayNameTransformation
import com.strato.hidrive.player.transformation.FileInfoToIntentTransformation
import com.strato.hidrive.player.transformation.FileInfoToLastModifiedDateTransformation
import com.strato.hidrive.player.transformation.FileInfoToMimetypeTransformation
import com.strato.hidrive.player.transformation.FileInfoToStringSizeTransformation
import com.strato.hidrive.player.transformation.FileInfoToUriTransformation
import com.strato.hidrive.player.transformation.MediaItemToDataSourceFactoryTransformation
import com.strato.hidrive.player.transformation.PlayerFileInfoToExifInfoProviderTransformation
import com.strato.hidrive.player.util.PlayerLocaleProvider
import com.strato.hidrive.player.views.PlayerCABStrategyProvider
import com.strato.hidrive.views.contextbar.toolbar.views.ToolbarItemViewFactory
import com.strato.hidrive.views.contextbar.toolbar.views.ToolbarItemViewFactoryImpl
import com.viseven.develop.player.interfaces.PlayerExceptionMessageProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import java.util.Optional
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [PlayerModule::class])
abstract class NCPlayerModule {

    companion object {
        @Named(PLAYER_CACHE_SIZE_QUALIFIER)
        @Provides
        fun providePlayerCacheSize(): Long = 300 * 1024 * 1024

        @Provides
        @Singleton
        fun providePlayerMultiplePlaybackSettings(
        ): PlayerMultiplePlaybackSettings {
            return NCMultiplePlaybackSettings(
                true
            )
        }

        @UnstableApi
        @Provides
        @Singleton
        fun provideHiDrivePlayerFactory(
            createExoPlayer: CreateExoPlayer,
            createChromeCastPlayer: CreateStubPlayer,
        ): PlayerFactory {
            return PlayerFactoryImpl(
                createExoPlayer,
                createChromeCastPlayer,
            )
        }

        @Provides
        @Singleton
        fun providePlayerChromecastModel(
        ): Optional<PlayerChromecastModel> {
            return Optional.empty()
        }

        @Provides
        @Singleton
        fun provideToolbarItemViewFactory(): ToolbarItemViewFactory{
            return ToolbarItemViewFactoryImpl()
        }
    }

    @Binds
    abstract fun bindFileInfoToIntentTransformation(
        transformation: FileInfoToIntentTransformationImpl,
    ): FileInfoToIntentTransformation

    @UnstableApi
    @Binds
    @Singleton
    abstract fun bindMediaItemToDataSourceFactoryTransformation(
        transformation: MediaItemToDataSourceFactoryTransformationImpl,
    ): MediaItemToDataSourceFactoryTransformation

    @Binds
    abstract fun bindIsVideoPredicate(
        predicate: IsVideoPredicateImpl
    ): IsVideoPredicate

    @Binds
    abstract fun bindFileInfoToMimetypeTransformation(
        transformation: FileInfoToMimetypeTransformationImpl
    ): FileInfoToMimetypeTransformation

    @Binds
    abstract fun bindFileInfoToStringSizeTransformation(
        transformation: FileInfoToStringSizeTransformationImpl
    ): FileInfoToStringSizeTransformation

    @Binds
    abstract fun bindFileInfoToUriTransformation(
        transformation: FileInfoToUriTransformationImpl
    ): FileInfoToUriTransformation

    @Binds
    @Singleton
    abstract fun bindPlayerPathProvider(
        provider: PlayerPathProviderImpl
    ): PlayerPathProvider

    @UnstableApi
    @Binds
    abstract fun bindFileInfoToCacheKeyTransformation(
        transformation: FileInfoToCacheKeyTransformationImpl
    ): FileInfoToCacheKeyTransformation

    @Binds
    abstract fun bindFileInfoToDisplayNameTransformation(
        transformation: FileInfoToDisplayNameTransformationImpl
    ): FileInfoToDisplayNameTransformation

    @Binds
    abstract fun bindFileInfoToLastModifiedDateTransformationImpl(
        transformation: FileInfoToLastModifiedDateTransformationImpl
    ): FileInfoToLastModifiedDateTransformation

    @Binds
    abstract fun bindPlayerLocaleProvider(
        provider: PlayerLocaleProviderImpl
    ): PlayerLocaleProvider

    @Binds
    abstract fun bindFileBeingProcessedPredicate(
        predicate: FileBeingProcessedPredicateImpl
    ): FileBeingProcessedPredicate

    @Binds
    abstract fun bindPlayerCABStrategyProvider(
        provider: PlayerCABStrategyProviderImpl
    ): PlayerCABStrategyProvider

    @Binds
    abstract fun bindPlayerFileInfoToExifInfoProviderTransformation(
        transformation: PlayerFileInfoToExifInfoProviderTransformationImpl,
    ): PlayerFileInfoToExifInfoProviderTransformation

    @Binds
    abstract fun bindExifInfoViewDependencies(
        impl: PlayerExifInfoViewDependenciesImpl
    ): PlayerExifInfoViewDependencies

    @Binds
    abstract fun bindPlayerMessageBuilderFactory(
        factory: PlayerMessageBuilderFactoryImpl
    ): PlayerMessageBuilderFactory

    @Binds
    abstract fun bindPlayerImageLoader(
        imageLoader: PlayerImageLoaderImpl
    ): PlayerImageLoader

    @Binds
    abstract fun bindPlayerExceptionMessageProvider(
        provider: NCPlayerExceptionMessageProvider,
    ): PlayerExceptionMessageProvider



    @Singleton
    @Binds
    abstract fun bindPlayerSourceInfoCache(
        cache: InMemoryPlayerSourceInfoCache
    ): PlayerSourceInfoCache

    @ContributesAndroidInjector
    abstract fun logsActivity(): IonosPlayerActivity
}