package com.ionos.player.di

import androidx.media3.common.util.UnstableApi
import com.ionos.player.NCMultiplePlaybackSettings
import com.ionos.player.activity.IonosPlayerActivity
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
import com.ionos.player.util.PlayerLocaleProviderImpl
import com.ionos.player.PlayerMultiplePlaybackSettings
import com.ionos.player.cache.PlayerPathProvider
import com.ionos.player.chromecast.PlayerChromecastModel
import com.ionos.player.di.PlayerModule.Companion.PLAYER_CACHE_SIZE_QUALIFIER
import com.ionos.player.image_loading.PlayerImageLoader
import com.ionos.player.media3.PlayerFactory
import com.ionos.player.media3.PlayerFactoryImpl
import com.ionos.player.media3.player.CreateExoPlayer
import com.ionos.player.media3.player.CreateStubPlayer
import com.ionos.player.message.PlayerMessageBuilderFactory
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlaybackSettings
import com.ionos.player.predicate.FileBeingProcessedPredicate
import com.ionos.player.predicate.IsVideoPredicate
import com.ionos.player.transformation.FileInfoToCacheKeyTransformation
import com.ionos.player.transformation.FileInfoToDisplayNameTransformation
import com.ionos.player.transformation.FileInfoToIntentTransformation
import com.ionos.player.transformation.FileInfoToLastModifiedDateTransformation
import com.ionos.player.transformation.FileInfoToMimetypeTransformation
import com.ionos.player.transformation.FileInfoToStringSizeTransformation
import com.ionos.player.transformation.FileInfoToUriTransformation
import com.ionos.player.transformation.MediaItemToDataSourceFactoryTransformation
import com.ionos.player.util.PlayerLocaleProvider
import com.ionos.player.player.interfaces.PlayerExceptionMessageProvider
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
            playbackSettings: MultiplePlaybackSettings,
        ): PlayerFactory {
            return PlayerFactoryImpl(
                createExoPlayer,
                createChromeCastPlayer,
                playbackSettings
            )
        }

        @Provides
        @Singleton
        fun providePlayerChromecastModel(
        ): Optional<PlayerChromecastModel> {
            return Optional.empty()
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