/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.resumption

import com.ionos.player.model.PlaybackFileType
import com.owncloud.android.ui.fragment.SearchType

data class PlaybackResumptionConfig(
    val currentFileId: String,
    val folderId: Long,
    val fileType: PlaybackFileType,
    val searchType: SearchType?,
)
