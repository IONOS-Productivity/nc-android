/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.datasource

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheKeyFactory
import androidx.media3.datasource.okhttp.OkHttpDataSource
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.network.ClientFactory
import com.owncloud.android.MainApp
import com.owncloud.android.datamodel.FileDataStorageManager
import javax.inject.Inject

class DataSourceFactory @Inject constructor(
    private val fileDataStorageManager: FileDataStorageManager,
    private val clientFactory: ClientFactory,
    private val accountManager: UserAccountManager,
    @UnstableApi private val cache: Cache,
) : DataSource.Factory {

    @UnstableApi
    override fun createDataSource(): DataSource {
        return CacheDataSource.Factory()
            .setUpstreamDataSourceFactory(createStreamDataSourceFactory())
            .setCache(cache)
            .setCacheKeyFactory(CacheKeyFactory.DEFAULT)
            .createDataSource()
    }

    @UnstableApi
    private fun createStreamDataSourceFactory() = DataSource.Factory {
        StreamDataSource(
            fileDataStorageManager = fileDataStorageManager,
            ownCloudClient = clientFactory.create(accountManager.user),
            fileDataSource = FileDataSource.Factory().createDataSource(),
            httpDataSource = createHttpDataSource(),
        )
    }

    @UnstableApi
    private fun createHttpDataSource() = OkHttpDataSource
        .Factory(clientFactory.createNextcloudClient(accountManager.user).client)
        .setUserAgent(MainApp.getUserAgent())
        .createDataSource()
}