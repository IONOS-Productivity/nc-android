/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.upload.target_provider

import java.io.Serializable

/**
 * User: Dima Muravyov
 * Date: 27.05.2019
 */
interface UploadTarget: Serializable {

    val uploadPath: String

}

data class ScanbotUploadTarget(
    override val uploadPath: String,
): UploadTarget