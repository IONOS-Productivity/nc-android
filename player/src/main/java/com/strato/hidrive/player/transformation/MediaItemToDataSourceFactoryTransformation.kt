package com.strato.hidrive.player.transformation

import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource

fun interface MediaItemToDataSourceFactoryTransformation
	: PlayerTransformation<MediaItem, DataSource.Factory>