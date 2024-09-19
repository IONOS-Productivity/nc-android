package com.ionos.player.transformation

import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import com.strato.hidrive.player.transformation.MediaItemToDataSourceFactoryTransformation
import javax.inject.Inject

class MediaItemToDataSourceFactoryTransformationImpl @Inject constructor(
): MediaItemToDataSourceFactoryTransformation {

    override fun transform(from: MediaItem): DataSource.Factory {
        TODO("Not yet implemented")
    }

}