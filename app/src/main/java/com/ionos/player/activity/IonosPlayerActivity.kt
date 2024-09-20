package com.ionos.player.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import com.nextcloud.client.di.Injectable
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.activity.BaseActivity
import com.strato.hidrive.player.util.SystemVersion

class IonosPlayerActivity : BaseActivity(), Injectable {

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

        fun parseResultIntent(result: Intent?): OCFile? {
            return if (SystemVersion.greaterOrEqualToTiramisu())
                result?.getParcelableExtra(RESULT_LAST_FILE_INFO, OCFile::class.java)
            else result?.getParcelableExtra(RESULT_LAST_FILE_INFO) as OCFile?
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(FrameLayout(baseContext))
    }



}