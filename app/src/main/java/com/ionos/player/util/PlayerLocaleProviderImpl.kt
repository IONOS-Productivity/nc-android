package com.ionos.player.util

import com.strato.hidrive.player.util.PlayerLocaleProvider
import java.util.Locale
import javax.inject.Inject

class PlayerLocaleProviderImpl @Inject constructor(): PlayerLocaleProvider {
    override fun getDefault(): Locale {
        return Locale.getDefault()
    }
}