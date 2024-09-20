package com.ionos.player.data_source

import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.files.StreamMediaFileOperation
import com.owncloud.android.lib.common.OwnCloudClient

@UnstableApi
internal class IonosDataSource(
    private val file: OCFile,
    private val oClient: OwnCloudClient,
    private val delegate: DataSource,
) : DataSource by delegate {

    private var uri: Uri? = null

    override fun open(dataSpec: DataSpec): Long {
        val sfo = StreamMediaFileOperation(file.localId)
        val result = sfo.execute(oClient)

        if (result.isSuccess) {
            val tmpUri = Uri.parse(result.data[0] as String)
            uri = tmpUri

            val dataSpecs = dataSpec.buildUpon()
                .setUri(tmpUri)
                .build()
            return delegate.open(dataSpecs)
        } else throw Exception("Failed to retrieve streaming uri")
    }

    override fun getUri(): Uri? {
        return uri
    }

}