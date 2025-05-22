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
            listeners[i].onUpdate(state)
        }
    }

    override fun onError(error: Throwable) {
        for (i in 0 until listeners.size) {
            listeners[i].onError(error)
        }
    }

    override fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>) {
        for (i in 0 until listeners.size) {
            listeners[i].onFilesChanged(originalFiles, currentFiles)
        }
    }
}
