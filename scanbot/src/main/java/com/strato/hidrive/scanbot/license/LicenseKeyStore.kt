package com.strato.hidrive.scanbot.license

import android.content.Context
import com.strato.hidrive.domain.optional.Optional
import com.strato.hidrive.scanbot.R
import javax.inject.Inject

internal class LicenseKeyStore @Inject constructor(
	context: Context,
	private val keyStore: KeyStore,
) {
	private val keyId = context.getString(R.string.scanbot_preference_license_key)

	fun getLicenseKey(): Optional<String> {
		return Optional.fromNullable(keyStore[keyId])
	}

	fun saveLicenseKey(licenseKey: String) {
		keyStore[keyId] = licenseKey
	}
}
