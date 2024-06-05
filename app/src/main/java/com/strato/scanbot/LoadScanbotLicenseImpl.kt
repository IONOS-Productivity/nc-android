package com.strato.scanbot

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.ionos.scanbot.license.LoadScanbotLicense
import javax.inject.Inject

class LoadScanbotLicenseImpl @Inject constructor(
    context: Context,
) : LoadScanbotLicense {

    private val workManager by lazy { WorkManager.getInstance(context) }

    override fun invoke() {
        val request =
            OneTimeWorkRequest.Builder(ScanbotLicenseDownloadWorker::class.java)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        workManager.enqueueUniqueWork(
            ScanbotLicenseDownloadWorker.SCANBOT_LICENSE_DOWNLOAD_WORKER,
            ExistingWorkPolicy.KEEP,
            request
        )
    }
}