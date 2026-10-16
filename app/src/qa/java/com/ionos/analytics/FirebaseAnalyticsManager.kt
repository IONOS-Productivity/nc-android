package com.ionos.analytics

import android.content.Context
import javax.inject.Inject

//STUB
class FirebaseAnalyticsManager @Inject constructor(
    private val context: Context,
) : AnalyticsManager {
    override fun setEnabled(enabled: Boolean) {
    }
}