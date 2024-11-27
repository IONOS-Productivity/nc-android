/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.license

import android.content.Context
import com.ionos.scanbot.R
import javax.inject.Inject

class LicenseKeyStore @Inject constructor(
	context: Context,
	private val keyStore: KeyStore,
) {
	private val keyId = context.getString(R.string.scanbot_preference_license_key)

	fun getLicenseKey(): String? {
		return keyStore[keyId]
	}

	fun saveLicenseKey(licenseKey: String) {
		keyStore[keyId] = licenseKey
	}
}
