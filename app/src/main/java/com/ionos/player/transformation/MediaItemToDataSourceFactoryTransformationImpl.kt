package com.ionos.player.transformation

import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import com.ionos.player.data_source.IonosDataSourceFactoryFactory
import com.ionos.player.transformation.oc_file.PlayerFileInfoToOCFileTransformation
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.MediaItemToDataSourceFactoryTransformation
import com.viseven.develop.media3.store.SourceInfoStore
import javax.inject.Inject

@UnstableApi
class MediaItemToDataSourceFactoryTransformationImpl @Inject constructor(
    private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
    private val transformation: PlayerFileInfoToOCFileTransformation,
    private val factory: IonosDataSourceFactoryFactory,
) : MediaItemToDataSourceFactoryTransformation {

    override fun transform(from: MediaItem): DataSource.Factory {
        val ocFile = sourceInfoStore
            .getSourceInfo(from.mediaId)
            ?.let(transformation::transform)

        return ocFile?.let(factory::create)
            ?: throw IllegalStateException("Can not create datasource from null OCFile")
    }

}