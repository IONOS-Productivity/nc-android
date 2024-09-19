package com.strato.hidrive.player.tracking

import com.strato.hidrive.player.util.PlayerSwipeDirection
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem
import com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker

interface VideoPlayerEventTracker : ExifInfoEventTracker{

	fun trackSwipe(direction: PlayerSwipeDirection)

	fun onToolbarItemClick(item: ToolbarItem)

	fun trackBackClicked()

	fun trackPlayNext()

	fun trackPlayPrevious()

	fun trackPlay()

	fun trackPause()

	fun trackRepeat()

	fun trackDoNotRepeat()

	fun trackShuffle()

	fun trackDoNotShuffle()

	fun trackRewind()

}