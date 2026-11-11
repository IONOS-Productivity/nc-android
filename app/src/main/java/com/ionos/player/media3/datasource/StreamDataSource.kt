/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.datasource

import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener
import com.ionos.player.model.getRemoteFileId
import com.owncloud.android.datamodel.FileDataStorageManager
import com.owncloud.android.files.StreamMediaFileOperation
import com.owncloud.android.lib.common.OwnCloudClient
import java.io.IOException

internal class StreamDataSource(
    private val fileDataStorageManager: FileDataStorageManager,
    private val ownCloudClient: OwnCloudClient,
    private val fileDataSource: DataSource,
    private val httpDataSource: DataSource,
) : DataSource {
    private var currentDataSource: DataSource? = null

    @UnstableApi
    override fun addTransferListener(listener: TransferListener) {
        fileDataSource.addTransferListener(listener)
        httpDataSource.addTransferListener(listener)
    }

    @UnstableApi
    override fun open(dataSpec: DataSpec): Long {
        val fileId = dataSpec.uri.getRemoteFileId() ?: throw IllegalArgumentException("Invalid URI: ${dataSpec.uri}")
        val file = fileDataStorageManager.getFileByLocalId(fileId)

        return if (file != null && file.isDown) {
            val uri = file.storageUri
            currentDataSource = fileDataSource
            fileDataSource.open(dataSpec.buildUpon(uri))
        } else {
            val sfo = StreamMediaFileOperation(fileId)
            val result = sfo.execute(ownCloudClient)
            if (result.isSuccess) {
                val uri = Uri.parse(result.data[0] as String)
                currentDataSource = httpDataSource
                httpDataSource.open(dataSpec.buildUpon(uri))
            } else {
                throw IOException("Failed to retrieve streaming uri", result.exception)
            }
        }
    }

    @UnstableApi
    override fun getUri(): Uri? {
        return currentDataSource?.uri
    }

    @UnstableApi
    override fun read(buffer: ByteArray, offset: Int, readLength: Int): Int {
        return currentDataSource?.read(buffer, offset, readLength) ?: throw IOException("DataSource not opened")
    }

    @UnstableApi
    override fun close() {
        fileDataSource.close()
        httpDataSource.close()
        currentDataSource = null
    }

    @UnstableApi
    private fun DataSpec.buildUpon(uri: Uri): DataSpec {
        return buildUpon()
            .setUri(uri)
            .build()
    }
}
