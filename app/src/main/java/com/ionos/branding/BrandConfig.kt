/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2026 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.branding

import kotlinx.serialization.Serializable

/**
 * Per-brand build configuration, injected by the Brander tool before the build.
 *
 * The file lives in the brand payload directories, so the asset merger picks the right one for the
 * built flavor (see doc/ADR-001-new-brand-variant.md):
 * - `app/src/brandIonos/assets/branding/config.json` for the IONOS brand,
 * - `app/src/brandStrato/assets/branding/config.json` for the HiDrive Next brand,
 * - `app/src/main/assets/branding/config.json` as the neutral default for non-branded flavors.
 *
 * Every field has a default so that adding a key to the file never breaks the flavors that do not
 * define it yet.
 */
@Serializable
data class BrandConfig(
    val version: Int = 1,
    val loginUrls: LoginUrls = LoginUrls()
)

@Serializable
data class LoginUrls(
  
    val byCountry: Map<String, String> = emptyMap(),
  
    val byLanguage: Map<String, String> = emptyMap(),
  
    val default: String = ""
)
