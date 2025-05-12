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
        listeners.forEach { it.onUpdate(state) }
    }

    override fun onError(error: Throwable) {
        listeners.forEach { it.onError(error) }
    }

    override fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>) {
        listeners.forEach { it.onFilesChanged(originalFiles, currentFiles) }
    }
}
