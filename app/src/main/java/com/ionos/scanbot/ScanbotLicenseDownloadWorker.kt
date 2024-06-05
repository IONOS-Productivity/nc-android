package com.ionos.scanbot

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ionos.scanbot.initializer.TryToInitScanbotSdk
import com.ionos.scanbot.license.LicenseKeyStore
import com.nextcloud.client.account.UserAccountManager
import com.owncloud.android.lib.common.OwnCloudClientManagerFactory

class ScanbotLicenseDownloadWorker(
    licenseUrl: String,
    private val accountManager: UserAccountManager,
    private val licenseKeyStore: LicenseKeyStore,
    private val tryToInitScanbotSdk: TryToInitScanbotSdk,
    private val context: Context,
    params: WorkerParameters,
): Worker(context, params){

    companion object {
        const val SCANBOT_LICENSE_DOWNLOAD_WORKER = "SCANBOT_LICENSE_DOWNLOAD_WORKER"
    }

    private val operation = DownloadLicenseRemoteOperation(licenseUrl)

    override fun doWork(): Result {
        return try {
            val ocAccount = accountManager.user.toOwnCloudAccount()
            val downloadClient =
                OwnCloudClientManagerFactory.getDefaultSingleton().getClientFor(ocAccount, context)

            val result = operation.execute(downloadClient)

            if (result.isSuccess) {
                operation.license?.let {
                    licenseKeyStore.saveLicenseKey(it)
                    tryToInitScanbotSdk(it)
                }
                Result.success()
            }
            else Result.retry()
        }catch (e: Exception){
            Result.failure()
        }
    }

}