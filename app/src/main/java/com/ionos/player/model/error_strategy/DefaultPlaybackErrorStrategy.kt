/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.error_strategy

import com.ionos.player.model.state.PlaybackState
import javax.inject.Inject

class DefaultPlaybackErrorStrategy @Inject constructor() : PlaybackErrorStrategy {

    override fun switchToNextSource(throwable: Throwable, playbackState: PlaybackState): Boolean {
        val currentFiles = playbackState.currentFiles
        val oneFileInQueue = currentFiles.size == 1
        val endOfQueue = playbackState.currentItemState
            .map { currentFiles.indexOf(it.file) == currentFiles.lastIndex }
            .orElse(false)
        return !oneFileInQueue && !endOfQueue
    }
}
