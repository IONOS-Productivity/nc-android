/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.network

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