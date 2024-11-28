package com.ionos.analycis

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsManager @Inject constructor(
    private val context: Context,
) : AnalyticsManager {

    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    override fun setEnabled(enabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
    }
}
