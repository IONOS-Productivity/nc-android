package com.ionos.privacy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.nextcloud.utils.extensions.getParcelableArgument
import com.owncloud.android.databinding.ActivityDataProtectionBinding
import com.owncloud.android.ui.activity.BaseActivity
import javax.inject.Inject

class DataProtectionActivity : BaseActivity() {

    @Inject
    lateinit var privacyPreferences: PrivacyPreferences

    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    private val binding by lazy { ActivityDataProtectionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.enableAnalyticsButton.setOnClickListener { onUserInteraction(analyticsEnabled = true) }
        binding.disableAnalyticsButton.setOnClickListener { onUserInteraction(analyticsEnabled = false) }
    }

    private fun onUserInteraction(analyticsEnabled: Boolean) {
        firebaseAnalytics.setAnalyticsCollectionEnabled(analyticsEnabled)
        privacyPreferences.setAnalyticsEnabled(analyticsEnabled)
        privacyPreferences.setDataProtectionProcessed(true)
        intent.getParcelableArgument(TARGET_SCREEN_INTENT_KEY, Intent::class.java)?.let(::startActivity)
        finish()
    }

    companion object {
        private const val TARGET_SCREEN_INTENT_KEY = "target_screen_intent"

        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, DataProtectionActivity::class.java)
        }

        @JvmStatic
        fun createIntent(context: Context, targetScreenIntent: Intent): Intent {
            return Intent(context, DataProtectionActivity::class.java)
                .putExtra(TARGET_SCREEN_INTENT_KEY, targetScreenIntent)
        }
    }
}
