package com.ionos.scanbot.initializer

import android.app.Application
import com.ionos.logger.LoggerUtil
import com.ionos.scanbot.di.qualifiers.ScanbotLicenseKey
import com.ionos.scanbot.license.LicenseKeyStore
import com.ionos.scanbot.license.LoadScanbotLicense
import com.ionos.scanbot.provider.FileProvider
import com.ionos.scanbot.provider.SdkProvider
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
    private val licenseKeyStore: LicenseKeyStore,
    private val sdkProvider: SdkProvider,
    private val tryToInitScanbotSdk: TryToInitScanbotSdk,
    private val loadScanbotLicense: LoadScanbotLicense,
	@ScanbotLicenseKey private val defaultLicenseKey: String,
) : ScanbotInitializer {

	override fun initialize() {
        tryToInitScanbotSdk(defaultLicenseKey)

		if (isSdkInitRequired()) {
			val savedKey = licenseKeyStore.getLicenseKey()
			if (savedKey.isPresent) {
                tryToInitScanbotSdk(savedKey.get())
			}

			if (isSdkInitRequired()) {
                loadScanbotLicense()
			}
		}
	}

	override fun isInitialized(): Boolean = ScanbotSDKInitializer.isInitialized()

	override fun isLicenseValid(): Boolean = sdkProvider.get().licenseInfo.isValid

	private fun isSdkInitRequired() = !isInitialized() || !isLicenseValid()

}