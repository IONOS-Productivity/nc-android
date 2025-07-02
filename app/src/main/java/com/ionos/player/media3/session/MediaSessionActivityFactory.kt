/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.session

import android.app.PendingIntent
import android.content.Context
import androidx.media3.common.MediaItem
import com.ionos.player.media3.common.playbackFile
import com.ionos.player.model.PlaybackFileType
import com.ionos.player.ui.PlayerActivity
import com.ionos.player.util.SystemVersion
import javax.inject.Inject

class MediaSessionActivityFactory @Inject constructor(
    private val context: Context,
) {

    fun create(currentMediaItem: MediaItem?): PendingIntent? {
        val currentFile = currentMediaItem?.mediaMetadata?.playbackFile ?: return null
        val fileType = PlaybackFileType.entries
            .firstOrNull { currentFile.mimeType.startsWith(it.value, ignoreCase = true) }
            ?: throw IllegalArgumentException("Unsupported file type: ${currentFile.mimeType}")

        val intent = PlayerActivity.createIntent(context, fileType)

        val requestCode = System.currentTimeMillis().toInt()

        return if (SystemVersion.greaterOrEqualToS()) {
            PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}
