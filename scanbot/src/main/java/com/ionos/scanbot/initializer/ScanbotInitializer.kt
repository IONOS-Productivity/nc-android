/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.initializer

interface ScanbotInitializer {

	fun initialize()

	fun isInitialized(): Boolean

	fun isLicenseValid(): Boolean
}
