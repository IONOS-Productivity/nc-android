package com.ionos.player.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ionos.player.font.PlayerCustomFonts
import com.ionos.player.tracking.AudioPlayerEventTrackerImpl
import com.ionos.player.tracking.VideoPlayerEventTrackerImpl
import com.nextcloud.client.di.Injectable
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.activity.BaseActivity
import com.strato.hidrive.player.util.SystemVersion
import com.strato.hidrive.player.views.AudioPlayerView
import com.strato.hidrive.player.views.VideoPlayerView
import com.strato.hidrive.player.views.player.view.PlayerViewContainer
import com.strato.hidrive.stylized_view.StylizedTextView
import javax.inject.Inject

class IonosPlayerActivity : BaseActivity(), PlayerViewContainer, Injectable {

    private enum class PlayerType {
        AUDIO,
        VIDEO
    }

    companion object{
        private const val PLAYER_TYPE: String = "PLAYER_TYPE"
        private const val RESULT_LAST_FILE_INFO: String = "RESULT_LAST_FILE_INFO"

        fun createVideoPlayerIntent(context: Context): Intent {
            return Intent(context, IonosPlayerActivity::class.java)
                .apply {
                    putExtra(PLAYER_TYPE, PlayerType.VIDEO)
                }
        }

        fun createAudioPlayerIntent(context: Context): Intent {
            return Intent(context, IonosPlayerActivity::class.java)
                .apply {
                    putExtra(PLAYER_TYPE, PlayerType.AUDIO)
                }
        }

        @Suppress("Deprecation")
        fun parseResultIntent(result: Intent?): OCFile? {
            return if (SystemVersion.greaterOrEqualToTiramisu())
                result?.getParcelableExtra(RESULT_LAST_FILE_INFO, OCFile::class.java)
            else result?.getParcelableExtra(RESULT_LAST_FILE_INFO) as OCFile?
        }

    }

    @Inject
    lateinit var audioPlayerEventTracker: AudioPlayerEventTrackerImpl
    @Inject
    lateinit var videoPlayerEventTracker: VideoPlayerEventTrackerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StylizedTextView.initialize(PlayerCustomFonts())

        when(getPlayerType()){
            PlayerType.AUDIO -> AudioPlayerView(this, audioPlayerEventTracker)
            PlayerType.VIDEO -> VideoPlayerView(this, videoPlayerEventTracker)
        }
            .let(this::setContentView)
    }

    @Suppress("Deprecation")
    private fun getPlayerType(): PlayerType {
        val type =
            if (SystemVersion.greaterOrEqualToTiramisu()) intent.getSerializableExtra(PLAYER_TYPE, PlayerType::class.java)
            else  intent.getSerializableExtra(PLAYER_TYPE) as PlayerType?

        return type ?: throw IllegalStateException("Player type was not defined")
    }

    override fun onPlayerViewClose() {
        finish()
    }

}