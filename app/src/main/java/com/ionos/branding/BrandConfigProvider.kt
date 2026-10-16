/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.branding

import android.content.Context
import com.owncloud.android.lib.common.utils.Log_OC
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Reads [BrandConfig] from the assets of the built flavor once and caches it.
 *
 * A missing or malformed file never crashes the app: it falls back to an empty configuration, which
 * makes the callers use their own defaults.
 */
@Singleton
class BrandConfigProvider @Inject constructor(private val context: Context) {

    val config: BrandConfig by lazy { read() }

    private fun read(): BrandConfig = runCatching {
        context.assets.open(ASSET_PATH).use { parse(it.reader().readText()) }
    }.getOrElse { error ->
        Log_OC.e(TAG, "Cannot read $ASSET_PATH, falling back to an empty config", error)
        BrandConfig()
    }

    companion object {
        private const val TAG = "BrandConfigProvider"
        private const val ASSET_PATH = "branding/config.json"

        private val json = Json { ignoreUnknownKeys = true }

        /** Kept separate from the asset access so that it can be covered by plain JVM tests. */
        fun parse(raw: String): BrandConfig = json.decodeFromString(raw)
    }
}
