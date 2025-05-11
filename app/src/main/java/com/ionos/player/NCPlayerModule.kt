package com.ionos.player

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import com.ionos.player.PlayerModule.Companion.PLAYER_CACHE_SIZE_QUALIFIER
import com.ionos.player.media3.datasource.StreamDataSourceFactory
import com.ionos.player.media3.session.DefaultMediaSessionActivityFactory
import com.ionos.player.media3.session.MediaSessionActivityFactory
import com.ionos.player.model.NCMultiplePlaybackSettings
import com.ionos.player.model.PlayerImageLoader
import com.ionos.player.model.PlayerImageLoaderImpl
import com.ionos.player.model.PlayerMultiplePlaybackSettings
import com.ionos.player.model.predicate.FileBeingProcessedPredicate
import com.ionos.player.model.predicate.FileBeingProcessedPredicateImpl
import com.ionos.player.model.predicate.IsVideoPredicate
import com.ionos.player.model.predicate.IsVideoPredicateImpl
import com.ionos.player.ui.IonosPlayerActivity
import com.ionos.player.ui.message.NCPlayerExceptionMessageProvider
import com.ionos.player.ui.message.PlayerExceptionMessageProvider
import com.ionos.player.ui.message.PlayerMessageBuilderFactory
import com.ionos.player.ui.message.PlayerMessageBuilderFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
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
    }

    @Binds
    abstract fun bindMediaSessionActivityFactory(
        factory: DefaultMediaSessionActivityFactory,
    ): MediaSessionActivityFactory

    @Binds
    @UnstableApi
    abstract fun bindDataSourceFactory(
        factory: StreamDataSourceFactory,
    ): DataSource.Factory

    @Binds
    abstract fun bindIsVideoPredicate(
        predicate: IsVideoPredicateImpl
    ): IsVideoPredicate

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

    @ContributesAndroidInjector
    abstract fun logsActivity(): IonosPlayerActivity
}