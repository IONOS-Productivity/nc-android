/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.authorization_method

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.VerificationModes.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.GrantPermissionRule
import com.blikoon.qrcodescanner.QrCodeActivity
import com.nextcloud.client.device.DeviceInfo
import com.owncloud.android.BuildConfig
import com.owncloud.android.R
import com.owncloud.android.authentication.DeepLinkLoginActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthorizationMethodActivityIT {

    @get:Rule
    val cameraPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Before
    fun setUp() {
        assumeTrue(BuildConfig.FLAVOR == IONOS_FLAVOR)
        assumeTrue(DeviceInfo().hasCamera(ApplicationProvider.getApplicationContext()))
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun scannedLoginQrCodeStartsDeepLinkLogin() {
        stubQrScanResult(LOGIN_QR_CODE)
        intending(hasComponent(DeepLinkLoginActivity::class.java.name))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        ActivityScenario.launch(AuthorizationMethodActivity::class.java).use {
            onView(withId(R.id.bLoginQr)).perform(click())

            intended(
                allOf(
                    hasComponent(DeepLinkLoginActivity::class.java.name),
                    hasAction(Intent.ACTION_VIEW),
                    hasData(LOGIN_QR_CODE.toUri())
                )
            )
        }
    }

    @Test
    fun scannedForeignQrCodeShowsErrorAndDoesNotStartLogin() {
        assertScannedQrCodeIsRejected(FOREIGN_QR_CODE)
    }

    @Test
    fun scannedIncompleteLoginQrCodeShowsErrorAndDoesNotStartLogin() {
        assertScannedQrCodeIsRejected(INCOMPLETE_LOGIN_QR_CODE)
    }

    private fun assertScannedQrCodeIsRejected(scannedText: String) {
        stubQrScanResult(scannedText)

        ActivityScenario.launch(AuthorizationMethodActivity::class.java).use {
            onView(withId(R.id.bLoginQr)).perform(click())

            onView(withText(R.string.qr_could_not_be_read)).check(matches(isDisplayed()))
            intended(hasComponent(DeepLinkLoginActivity::class.java.name), times(0))
        }
    }

    private fun stubQrScanResult(scannedText: String) {
        val resultData = Intent().putExtra(QR_SCAN_RESULT_KEY, scannedText)
        intending(hasComponent(QrCodeActivity::class.java.name))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, resultData))
    }

    companion object {
        private const val IONOS_FLAVOR = "gplay"
        private const val QR_SCAN_RESULT_KEY = "com.blikoon.qrcodescanner.got_qr_scan_relult"
        private const val LOGIN_QR_CODE = "nc://login/user:qa&password:app-password&server:https://cloud.example.com"
        private const val FOREIGN_QR_CODE = "https://www.example.com"
        private const val INCOMPLETE_LOGIN_QR_CODE = "nc://login/server:https://cloud.example.com"
    }
}
