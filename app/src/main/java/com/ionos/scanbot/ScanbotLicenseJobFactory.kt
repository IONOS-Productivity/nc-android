package com.ionos.scanbot

import android.content.Context
import androidx.work.WorkerParameters
import com.ionos.scanbot.di.qualifiers.ScanbotLicenseKeyUrl
import com.ionos.scanbot.initializer.TryToInitScanbotSdk
import com.ionos.scanbot.license.LicenseKeyStore
import com.nextcloud.client.account.UserAccountManager
import javax.inject.Inject

class ScanbotLicenseJobFactory @Inject constructor(
    @ScanbotLicenseKeyUrl private val scanbotLicenseUrl: String,
    private val accountManager: UserAccountManager,
    private val licenseKeyStore: LicenseKeyStore,
    private val tryToInitScanbotSdk: TryToInitScanbotSdk,
) {

    fun create(context: Context, params: WorkerParameters): ScanbotLicenseDownloadWorker {
        return ScanbotLicenseDownloadWorker(
            scanbotLicenseUrl,
            accountManager,
            licenseKeyStore,
            tryToInitScanbotSdk,
            context,
            params
        )
    }
}