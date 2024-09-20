package com.ionos.player.tracking

import android.content.Context
import com.strato.hidrive.player.tracking.VideoPlayerEventTracker
import com.strato.hidrive.player.util.PlayerSwipeDirection
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem
import javax.inject.Inject

class VideoPlayerEventTrackerImpl @Inject constructor(
): VideoPlayerEventTracker {

    override fun trackSwipe(direction: PlayerSwipeDirection) {
    }

    override fun onToolbarItemClick(item: ToolbarItem) {
    }

    override fun trackBackClicked() {
    }

    override fun trackPlayNext() {
    }

    override fun trackPlayPrevious() {
    }

    override fun trackPlay() {
    }

    override fun trackPause() {
    }

    override fun trackRepeat() {
    }

    override fun trackDoNotRepeat() {
    }

    override fun trackShuffle() {
    }

    override fun trackDoNotShuffle() {
    }

    override fun trackRewind() {
    }

    override fun trackCancel(context: Context) {
    }

}