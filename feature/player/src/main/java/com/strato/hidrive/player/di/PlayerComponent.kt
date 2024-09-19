package com.strato.hidrive.player.di

import android.content.Context
import com.strato.hidrive.player.views.AudioPlayerView
import com.strato.hidrive.player.views.VideoPlayerView
import com.strato.hidrive.player.views.player.fragment.AudioPlayerSourceFragment
import com.strato.hidrive.player.views.player.fragment.VideoPlayerSourceFragment
import com.strato.hidrive.player.views.player.view.FileTextDetailView
import com.strato.hidrive.player.views.player.view.PlayerControlView
import com.strato.hidrive.player.views.player.view.PlayerSourcesView

interface PlayerComponent {

	companion object{
		fun from(from: Context?): PlayerComponent {
			return (from?.applicationContext as? PlayerComponentProvider)
				?.getComponent()
				?: throw IllegalStateException("No available context")
		}
	}

	fun inject(playerSourcesView: PlayerSourcesView)

	fun inject(audioPlayerSourceFragment: AudioPlayerSourceFragment)

	fun inject(fileTextDetailView: FileTextDetailView)

	fun inject(videoPlayerSourceFragment: VideoPlayerSourceFragment)

	fun inject(playerControlView: PlayerControlView)

	fun inject(audioPlayerView: AudioPlayerView)

	fun inject(videoPlayerView: VideoPlayerView)

}