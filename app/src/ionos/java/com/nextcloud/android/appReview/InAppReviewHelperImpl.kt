/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */
package com.nextcloud.android.appReview

import androidx.appcompat.app.AppCompatActivity
import com.nextcloud.appReview.InAppReviewHelper
import com.nextcloud.client.preferences.AppPreferences

class InAppReviewHelperImpl(appPreferences: AppPreferences) :
    InAppReviewHelper {
    override fun resetAndIncrementAppRestartCounter() {
    }

    override fun showInAppReview(activity: AppCompatActivity) {
    }
}
