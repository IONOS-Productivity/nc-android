package com.ionos.player.model

class NCMultiplePlaybackSettings(
    shouldRepeatAll: Boolean,
) : PlayerMultiplePlaybackSettings(shouldRepeatAll) {
    override fun isRepeatSingle(): Boolean {
        return false
    }

    override fun repeatSingle() {
    }

    override fun doNotRepeatSingle() {
    }

    override fun isShuffle(): Boolean {
        return false
    }

    override fun shuffle() {
    }

    override fun doNotShuffle() {
    }
}