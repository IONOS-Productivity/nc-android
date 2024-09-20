package com.ionos.player.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import com.nextcloud.client.di.Injectable
import com.owncloud.android.ui.activity.BaseActivity

class IonosPlayerActivity : BaseActivity(), Injectable {

    private enum class PlayerType {
        AUDIO,
        VIDEO
    }

    companion object{
        private const val PLAYER_TYPE: String = "PLAYER_TYPE"

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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(FrameLayout(baseContext))
    }



}