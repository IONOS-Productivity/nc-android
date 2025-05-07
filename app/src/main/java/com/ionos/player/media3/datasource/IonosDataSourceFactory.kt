package com.ionos.player.media3.datasource

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheKeyFactory
import androidx.media3.datasource.okhttp.OkHttpDataSource
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.network.ClientFactory
import com.owncloud.android.MainApp
import com.owncloud.android.datamodel.OCFile
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@UnstableApi
@AssistedFactory
interface IonosDataSourceFactoryFactory{
    fun create(
        file: OCFile,
    ): IonosDataSourceFactory
}

@UnstableApi
class IonosDataSourceFactory @AssistedInject constructor(
    @Assisted private val file: OCFile,
    private val clientFactory: ClientFactory,
    private val accountManager: UserAccountManager,
    private val cache: Cache,
) : DataSource.Factory {

    override fun createDataSource(): DataSource {
        val nextcloudClient = clientFactory.createNextcloudClient(accountManager.user)
        val ownCloudClient = clientFactory.create(accountManager.user)

        val okHttpDataSourceFactory = OkHttpDataSource.Factory(nextcloudClient.client)
        okHttpDataSourceFactory.setUserAgent(MainApp.getUserAgent())

        val cached = CacheDataSource.Factory()
            .setUpstreamDataSourceFactory(okHttpDataSourceFactory)
            .setCache(cache)
            .setCacheKeyFactory(CacheKeyFactory.DEFAULT)
            .createDataSource()

        return IonosDataSource(file, ownCloudClient, cached)
    }

}