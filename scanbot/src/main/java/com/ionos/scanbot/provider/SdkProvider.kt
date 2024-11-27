/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.provider

import android.content.Context
import io.scanbot.sdk.ScanbotSDK
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SdkProvider @Inject constructor(
	private val context: Context,
) {
	private val sdk by lazy { ScanbotSDK(context) }

	fun get() = sdk
}
