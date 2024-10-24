package com.ionos.scanbot.availability

import com.ionos.scanbot.BuildConfig
import javax.inject.Inject

class ScanbotFeatureAvailability @Inject constructor(): Availability {

    override fun available(): Boolean {
        return BuildConfig.IS_SCANBOT_FEATURE_AVAILABLE
    }

}