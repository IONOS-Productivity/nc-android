/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

import java.io.Serializable

data class PlaybackFile(
    val id: String,
    val uri: String,
    val name: String,
    val mimeType: String,
    val contentLength: Long,
    val lastModified: Long,
    val isFavorite: Boolean,
) : Serializable {
    fun getNameWithoutExtension(): String {
        return name.substringBeforeLast(".")
    }
}
