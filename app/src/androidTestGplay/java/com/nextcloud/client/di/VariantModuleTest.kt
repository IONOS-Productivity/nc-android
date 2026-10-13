/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2026 Nextcloud GmbH and Nextcloud contributors
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.nextcloud.client.di

import com.ionos.scanbot.availability.Availability
import com.ionos.scanbot.di.qualifiers.Scanbot
import com.ionos.scanbot.di.qualifiers.ScanbotLicense
import com.nextcloud.client.documentscan.AppScanOptionalFeature
import dagger.BindsInstance
import dagger.Component
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class VariantModuleTest {

    @Test
    fun testAppScanWhenScanbotFeatureAndLicenseAvailableShouldBeAvailable() {
        val feature = createAppScanOptionalFeature(isFeatureAvailable = true, isLicenseAvailable = true)

        assertTrue(feature.isAvailable)
    }

    @Test
    fun testAppScanWhenScanbotFeatureNotAvailableShouldNotBeAvailable() {
        val feature = createAppScanOptionalFeature(isFeatureAvailable = false, isLicenseAvailable = true)

        assertFalse(feature.isAvailable)
    }

    @Test
    fun testAppScanWhenScanbotLicenseNotAvailableShouldNotBeAvailable() {
        val feature = createAppScanOptionalFeature(isFeatureAvailable = true, isLicenseAvailable = false)

        assertFalse(feature.isAvailable)
    }

    @Test
    fun testAppScanShouldNotProvideScanContract() {
        val feature = createAppScanOptionalFeature(isFeatureAvailable = true, isLicenseAvailable = true)

        assertThrows(UnsupportedOperationException::class.java) { feature.getScanContract() }
    }

    private fun createAppScanOptionalFeature(
        isFeatureAvailable: Boolean,
        isLicenseAvailable: Boolean
    ): AppScanOptionalFeature = DaggerVariantModuleTest_TestVariantComponent.factory()
        .create(Availability { isFeatureAvailable }, Availability { isLicenseAvailable })
        .appScanOptionalFeature()

    @Component(modules = [VariantModule::class])
    interface TestVariantComponent {
        fun appScanOptionalFeature(): AppScanOptionalFeature

        @Component.Factory
        interface Factory {
            fun create(
                @BindsInstance @Scanbot featureAvailability: Availability,
                @BindsInstance @ScanbotLicense licenseAvailability: Availability
            ): TestVariantComponent
        }
    }
}
