package com.ionos.player.cab

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.views.PlayerCABStrategyProvider
import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy
import com.strato.hidrive.views.contextbar.strategy.configuration.NullCABConfigurationStrategy
import io.reactivex.Single
import javax.inject.Inject

class PlayerCABStrategyProviderImpl @Inject constructor() : PlayerCABStrategyProvider {

    override fun emptyNavigationBarStrategy(): ICABConfigurationStrategy {
        return NullCABConfigurationStrategy.getInstance()
    }

    override fun mediaPlayerTopNavigationStrategy(file: PlayerFileInfo): Single<ICABConfigurationStrategy> {
        return Single.fromCallable { emptyNavigationBarStrategy() }
    }
}