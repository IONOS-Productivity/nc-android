package com.ionos.player

import android.content.Context
import com.ionos.player.media3.PlaybackServiceComponent
import com.ionos.player.ui.audio.AudioPlayerSourceFragment
import com.ionos.player.ui.audio.AudioPlayerView
import com.ionos.player.ui.common.FileTextDetailView
import com.ionos.player.ui.control.PlayerControlView
import com.ionos.player.ui.sources.PlayerSourcesView
import com.ionos.player.ui.video.VideoPlayerSourceFragment
import com.ionos.player.ui.video.VideoPlayerView

interface PlayerComponent : PlaybackServiceComponent {

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