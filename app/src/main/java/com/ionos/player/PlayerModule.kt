/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.ionos.player.media3.PlaybackModelImpl
import com.ionos.player.media3.PlaybackService
import com.ionos.player.media3.common.PlayerFactory
import com.ionos.player.media3.exoplayer.ExoPlayerFactory
import com.ionos.player.media3.session.MediaSessionHolder
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.error_strategy.DefaultPlaybackErrorStrategy
import com.ionos.player.model.error_strategy.PlaybackErrorStrategy
import com.ionos.player.ui.PlayerActivity
import com.ionos.player.ui.audio.AudioFileFragment
import com.ionos.player.ui.audio.AudioPlayerView
import com.ionos.player.ui.common.PlayerProgressIndicator
import com.ionos.player.ui.control.PlayerControlView
import com.ionos.player.ui.video.VideoFileFragment
import com.ionos.player.ui.video.VideoPlayerView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import java.io.File
import javax.inject.Singleton

@Module
abstract class PlayerModule {

    companion object {
        private const val PLAYER_CACHE_DIR_NAME = "player"
        private const val PLAYER_CACHE_SIZE = 300 * 1024 * 1024L

        @UnstableApi
        @Singleton
        @Provides
        fun provideCache(context: Context): Cache {
            return SimpleCache(
                File(context.cacheDir, PLAYER_CACHE_DIR_NAME),
                LeastRecentlyUsedCacheEvictor(PLAYER_CACHE_SIZE)
            )
        }
    }

    @Binds
    @Singleton
    abstract fun bindPlaybackModel(
        model: PlaybackModelImpl,
    ): PlaybackModel

    @Binds
    @Singleton
    abstract fun mediaSessionHolder(
        playbackModel: PlaybackModelImpl,
    ): MediaSessionHolder

    @Binds
    abstract fun bindPlayerFactory(
        playerFactory: ExoPlayerFactory,
    ): PlayerFactory

    @Binds
    abstract fun bindPlaybackErrorStrategy(
        strategy: DefaultPlaybackErrorStrategy,
    ): PlaybackErrorStrategy

    @ContributesAndroidInjector
    abstract fun playbackService(): PlaybackService

    @ContributesAndroidInjector
    abstract fun playerActivity(): PlayerActivity

    @ContributesAndroidInjector
    abstract fun audioPlayerView(): AudioPlayerView

    @ContributesAndroidInjector
    abstract fun videoPlayerView(): VideoPlayerView

    @ContributesAndroidInjector
    abstract fun playerControlView(): PlayerControlView

    @ContributesAndroidInjector
    abstract fun playerProgressIndicator(): PlayerProgressIndicator

    @ContributesAndroidInjector
    abstract fun audioFileFragment(): AudioFileFragment

    @ContributesAndroidInjector
    abstract fun videoFileFragment(): VideoFileFragment
}