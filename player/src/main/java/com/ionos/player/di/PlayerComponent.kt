package com.ionos.player.di

import android.content.Context
import com.ionos.player.views.AudioPlayerView
import com.ionos.player.views.VideoPlayerView
import com.ionos.player.views.player.fragment.AudioPlayerSourceFragment
import com.ionos.player.views.player.fragment.VideoPlayerSourceFragment
import com.ionos.player.views.player.view.FileTextDetailView
import com.ionos.player.views.player.view.PlayerControlView
import com.ionos.player.views.player.view.PlayerSourcesView

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