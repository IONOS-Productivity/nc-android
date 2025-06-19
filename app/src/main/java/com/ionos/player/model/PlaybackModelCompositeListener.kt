/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

import com.ionos.player.model.state.PlaybackState

class PlaybackModelCompositeListener : PlaybackModel.Listener {
    private val listeners = mutableListOf<PlaybackModel.Listener>()

    fun addListener(listener: PlaybackModel.Listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    fun removeListener(listener: PlaybackModel.Listener?) {
        listeners.remove(listener)
    }

    override fun onUpdate(state: PlaybackState) {
        for (i in 0 until listeners.size) {
            listeners.getOrNull(i)?.onUpdate(state)
        }
    }

    override fun onError(error: Throwable) {
        for (i in 0 until listeners.size) {
            listeners.getOrNull(i)?.onError(error)
        }
    }

    override fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>) {
        for (i in 0 until listeners.size) {
            listeners.getOrNull(i)?.onFilesChanged(originalFiles, currentFiles)
        }
    }
}
