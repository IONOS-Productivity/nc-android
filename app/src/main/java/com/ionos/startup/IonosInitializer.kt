/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.startup

import android.content.Context
import androidx.startup.Initializer
import com.ionos.analycis.AnalyticsManager
import com.ionos.privacy.PrivacyPreferences
import com.ionos.scanbot.initializer.ScanbotInitializer
import com.owncloud.android.MainApp
import javax.inject.Inject

class IonosInitializer : Initializer<Unit> {

    @Inject
    lateinit var privacyPreferences: PrivacyPreferences

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    @Inject
    lateinit var scanbotInitializer: ScanbotInitializer

    override fun create(context: Context) {
        (context.applicationContext as MainApp).androidInjector().inject(this)
        scanbotInitializer.initialize()
        analyticsManager.setEnabled(privacyPreferences.isAnalyticsEnabled())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
