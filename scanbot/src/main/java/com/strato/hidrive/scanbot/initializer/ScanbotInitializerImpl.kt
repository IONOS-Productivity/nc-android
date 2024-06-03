package com.strato.hidrive.scanbot.initializer

import android.app.Application
import com.strato.hidrive.domain.logger.LoggerUtil
import com.strato.hidrive.scanbot.di.qualifiers.ScanbotLicenseKey
import com.strato.hidrive.scanbot.license.LicenseLoader
import com.strato.hidrive.scanbot.provider.FileProvider
import com.strato.hidrive.scanbot.provider.SdkProvider
import io.reactivex.schedulers.Schedulers
import io.scanbot.sdk.ScanbotSDKInitializer
import io.scanbot.sdk.util.log.StubLogger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * User: Dima Muravyov
 * Date: 07.02.2020
 */
@Singleton
class ScanbotInitializerImpl @Inject internal constructor(
	private val application: Application,
	private val licenseLoader: LicenseLoader,
	private val sdkProvider: SdkProvider,
	private val fileProvider: FileProvider,
	@ScanbotLicenseKey private val defaultLicenseKey: String,
) : ScanbotInitializer {

	override fun initialize() {
		tryToInitSdk(application, defaultLicenseKey)

		if (isSdkInitRequired()) {
			val savedKey = licenseLoader.getSavedLicenseKey()
			if (savedKey.isPresent) {
				tryToInitSdk(application, savedKey.get())
			}

			if (isSdkInitRequired()) {
				licenseLoader.load(Schedulers.io()) { loadedKey ->
					tryToInitSdk(application, loadedKey)
				}
			}
		}
	}

	override fun isInitialized(): Boolean = ScanbotSDKInitializer.isInitialized()

	override fun isLicenseValid(): Boolean = sdkProvider.get().licenseInfo.isValid

	private fun isSdkInitRequired() = !isInitialized() || !isLicenseValid()

	private fun tryToInitSdk(application: Application, licenseKey: String) {
		try {
			ScanbotSDKInitializer()
				.sdkFilesDirectory(application, fileProvider.sdkFilesDirectory)
				.logger(StubLogger())
				.license(application, licenseKey)
				.prepareOCRLanguagesBlobs(true)
				.initialize(application)
		} catch (exception: RuntimeException) {
			LoggerUtil.logE(ScanbotInitializer::class.java.simpleName, exception.toString())
		}
	}
}