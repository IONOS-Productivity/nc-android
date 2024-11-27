/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.initializer

import android.app.Application
import com.ionos.scanbot.provider.FileProvider
import com.ionos.scanbot.util.logger.LoggerUtil
import io.scanbot.sdk.ScanbotSDKInitializer
import io.scanbot.sdk.util.log.StubLogger
import javax.inject.Inject

class TryToInitScanbotSdk @Inject constructor(
    private val application: Application,
    private val fileProvider: FileProvider,
){

    operator fun invoke(licenseKey: String) {
        try {
            ScanbotSDKInitializer()
                .sdkFilesDirectory(application, fileProvider.sdkFilesDirectory)
                .logger(StubLogger())
                .license(application, licenseKey)
                .prepareOCRLanguagesBlobs(true)
                .initialize(application)
        } catch (exception: RuntimeException) {
            LoggerUtil.logE(ScanbotInitializer::class.java.simpleName, exception)
        }
    }
}