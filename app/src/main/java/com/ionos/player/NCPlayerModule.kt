package com.ionos.player

import androidx.media3.datasource.DataSource
import com.ionos.player.media3.datasource.StreamDataSourceFactory
import com.ionos.player.media3.session.DefaultMediaSessionActivityFactory
import com.ionos.player.media3.session.MediaSessionActivityFactory
import com.ionos.player.model.ThumbnailLoader
import com.ionos.player.model.ThumbnailLoaderImpl
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
import dagger.android.ContributesAndroidInjector

@Module(includes = [PlayerModule::class])
abstract class NCPlayerModule {

    @Binds
    abstract fun bindMediaSessionActivityFactory(
        factory: DefaultMediaSessionActivityFactory,
    ): MediaSessionActivityFactory

    @Binds
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
    abstract fun bindThumbnailLoader(
        imageLoader: ThumbnailLoaderImpl
    ): ThumbnailLoader

    @Binds
    abstract fun bindPlayerExceptionMessageProvider(
        provider: NCPlayerExceptionMessageProvider,
    ): PlayerExceptionMessageProvider

    @ContributesAndroidInjector
    abstract fun logsActivity(): IonosPlayerActivity
}