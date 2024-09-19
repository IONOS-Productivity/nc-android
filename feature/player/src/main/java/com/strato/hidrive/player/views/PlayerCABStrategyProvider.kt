package com.strato.hidrive.player.views

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy
import io.reactivex.Single


interface PlayerCABStrategyProvider {

	fun emptyNavigationBarStrategy(): ICABConfigurationStrategy

	fun mediaPlayerTopNavigationStrategy(
		file: PlayerFileInfo
	): Single<ICABConfigurationStrategy>

}