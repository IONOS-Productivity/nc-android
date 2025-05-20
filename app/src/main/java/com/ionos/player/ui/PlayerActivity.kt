package com.ionos.player.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ionos.player.model.PlaybackFileType
import com.ionos.player.ui.PlayerScreenEvent.ShowFileActions
import com.ionos.player.ui.PlayerScreenEvent.ShowFileDetails
import com.ionos.player.ui.audio.AudioPlayerView
import com.ionos.player.ui.video.VideoPlayerView
import com.ionos.player.ui.video.surface.PlayerCompatible
import com.ionos.player.ui.video.surface.SurfaceInvalidator
import com.ionos.player.util.SystemVersion
import com.nextcloud.client.di.Injectable
import com.nextcloud.ui.fileactions.FileAction
import com.nextcloud.ui.fileactions.FileActionsBottomSheet
import com.owncloud.android.R
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.activity.FileActivity
import com.owncloud.android.ui.activity.FileDisplayActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlayerActivity : FileActivity(), PlayerViewContainer, PlayerCompatible, Injectable {

    companion object {
        private const val PLAYBACK_FILE_TYPE: String = "PLAYBACK_FILE_TYPE"

        fun createIntent(context: Context, playbackFileType: PlaybackFileType): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(PLAYBACK_FILE_TYPE, playbackFileType)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: PlayerViewModel.Factory
    private val viewModel by viewModels<PlayerViewModel> { viewModelFactory }

    private val surfaceInvalidator = SurfaceInvalidator()
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerView = when (getPlaybackFileType()) {
            PlaybackFileType.AUDIO -> AudioPlayerView(this)
            PlaybackFileType.VIDEO -> VideoPlayerView(this)
        }
        setContentView(playerView)

        val moreButton = findViewById<View>(R.id.more)
        moreButton.setOnClickListener { viewModel.onMoreButtonClick() }

        viewModel.eventFlow
            .flowWithLifecycle(lifecycle)
            .onEach { handleEvent(it) }
            .launchIn(lifecycleScope)
    }

    @Suppress("Deprecation")
    private fun getPlaybackFileType(): PlaybackFileType {
        val playbackFileType = if (SystemVersion.greaterOrEqualToTiramisu()) {
            intent.getSerializableExtra(PLAYBACK_FILE_TYPE, PlaybackFileType::class.java)
        } else {
            intent.getSerializableExtra(PLAYBACK_FILE_TYPE) as PlaybackFileType?
        }
        return playbackFileType ?: throw IllegalStateException("Playback file type was not defined")
    }

    override fun onStart() {
        super.onStart()
        playerView.onStart()
    }

    override fun onStop() {
        playerView.onStop()
        super.onStop()
    }

    override fun onPlayerViewClose() {
        finish()
    }

    override fun getSurfaceInvalidator(): SurfaceInvalidator {
        return surfaceInvalidator
    }

    private fun handleEvent(event: PlayerScreenEvent) {
        when (event) {
            is ShowFileActions -> showFileActions(event.file)
            is ShowFileDetails -> showFileDetails(event.file)
        }
    }

    private fun showFileActions(file: OCFile) {
        val availableActions = listOf(
            R.id.action_see_details,
        )

        val actionsToHide = FileAction.SORTED_VALUES
            .map { it.id }
            .filter { it !in availableActions }

        FileActionsBottomSheet.newInstance(file, false, actionsToHide)
            .setResultListener(supportFragmentManager, this) { viewModel.onFileActionChosen(file, it) }
            .show(supportFragmentManager, "actions")
    }

    fun showFileDetails(file: OCFile) {
        val intent = Intent(this, FileDisplayActivity::class.java).apply {
            action = FileDisplayActivity.ACTION_DETAILS
            putExtra(EXTRA_FILE, file)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        finish()
    }
}
