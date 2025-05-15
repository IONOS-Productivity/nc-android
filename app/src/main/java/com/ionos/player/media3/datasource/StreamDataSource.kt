package com.ionos.player.media3.datasource

import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import com.ionos.player.model.getRemoteFileId
import com.owncloud.android.files.StreamMediaFileOperation
import com.owncloud.android.lib.common.OwnCloudClient
import java.io.IOException

internal class StreamDataSource(
    private val oClient: OwnCloudClient,
    private val delegate: DataSource,
) : DataSource by delegate {

    private var uri: Uri? = null

    @UnstableApi
    override fun open(dataSpec: DataSpec): Long {
        val fileId = dataSpec.uri.getRemoteFileId() ?: throw IllegalArgumentException("Invalid URI: ${dataSpec.uri}")
        val sfo = StreamMediaFileOperation(fileId)
        val result = sfo.execute(oClient)

        if (result.isSuccess) {
            val tmpUri = Uri.parse(result.data[0] as String)
            uri = tmpUri

            val dataSpecs = dataSpec.buildUpon()
                .setUri(tmpUri)
                .build()
            return delegate.open(dataSpecs)
        } else throw IOException("Failed to retrieve streaming uri", result.exception)
    }

    @UnstableApi
    override fun getUri(): Uri? {
        return uri
    }

}