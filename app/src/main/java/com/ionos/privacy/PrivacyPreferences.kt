package com.ionos.privacy

import android.content.Context
import javax.inject.Inject

class PrivacyPreferences @Inject constructor(
    private val context: Context,
) {
    private val sharedPreferences by lazy { context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE) }

    fun isDataProtectionProcessed(): Boolean {
        return sharedPreferences.getBoolean(DATA_PROTECTION_PROCESSED_KEY, false)
    }

    fun setDataProtectionProcessed(value: Boolean) {
        sharedPreferences.edit().putBoolean(DATA_PROTECTION_PROCESSED_KEY, value).apply()
    }

    fun isAnalyticsEnabled(): Boolean {
        return sharedPreferences.getBoolean(ANALYTICS_ENABLED_KEY, false)
    }

    fun setAnalyticsEnabled(value: Boolean) {
        sharedPreferences.edit().putBoolean(ANALYTICS_ENABLED_KEY, value).apply()
    }

    companion object {
        private const val FILE_NAME = "privacy_preferences"
        private const val DATA_PROTECTION_PROCESSED_KEY = "data_protection_processed"
        private const val ANALYTICS_ENABLED_KEY = "analytics_enabled"
    }
}
