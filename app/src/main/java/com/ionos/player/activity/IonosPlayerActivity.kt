package com.ionos.player.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nextcloud.client.di.Injectable
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.activity.BaseActivity
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.util.SystemVersion
import com.strato.hidrive.player.views.AudioPlayerView
import com.strato.hidrive.player.views.VideoPlayerView
import com.strato.hidrive.player.views.player.fragment.SurfaceInvalidator
import com.strato.hidrive.player.views.player.view.PlayerCompatible
import com.strato.hidrive.player.views.player.view.PlayerView
import com.strato.hidrive.player.views.player.view.PlayerViewContainer

class IonosPlayerActivity : BaseActivity(), PlayerViewContainer, PlayerCompatible, Injectable {

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


    private val surfaceInvalidator = SurfaceInvalidator()
    private lateinit var playerView: PlayerView
    private var isPlayerViewStarted = false
    private var currentFileInfo: PlayerFileInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerView = when(getPlayerType()){
            PlayerType.AUDIO -> AudioPlayerView(this)
            PlayerType.VIDEO -> VideoPlayerView(this)
        }
        setContentView(playerView)
    }

    @Suppress("Deprecation")
    private fun getPlayerType(): PlayerType {
        val type =
            if (SystemVersion.greaterOrEqualToTiramisu()) intent.getSerializableExtra(PLAYER_TYPE, PlayerType::class.java)
            else  intent.getSerializableExtra(PLAYER_TYPE) as PlayerType?

        return type ?: throw IllegalStateException("Player type was not defined")
    }

    override fun onStart() {
        super.onStart()
        if (!this.isPlayerViewStarted) {
            playerView.onStart()
            playerView.setCurrentFileListener{ currentFileInfo = it}
            this.isPlayerViewStarted = true
        }
    }

    override fun onStop() {
        playerView.setCurrentFileListener(null)
        playerView.onStop()
        this.isPlayerViewStarted = false
        super.onStop()
    }

    // todo by mahera: try to replace with launchers
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        playerView.onStart()
        this.isPlayerViewStarted = true
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPlayerViewClose() {
        finish()
    }

    override fun getSurfaceInvalidator(): SurfaceInvalidator {
        return surfaceInvalidator
    }

}