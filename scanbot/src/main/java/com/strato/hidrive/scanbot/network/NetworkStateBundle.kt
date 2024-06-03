package com.strato.hidrive.scanbot.network

/**
 * Created with love by Yehor Levchenko.
 * Date: 26.01.2022.
 */
data class NetworkStateBundle(
    val online: Boolean,
    val wifiAvailable: Boolean,
    val mobileAvailable: Boolean,
    val wasOfflineBefore: Boolean
)