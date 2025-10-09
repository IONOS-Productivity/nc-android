/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ionos.player.model.PlaybackFileType
import com.ionos.player.model.PlaybackModel
import com.ionos.player.ui.PlayerScreenEvent.LaunchOpenFileIntent
import com.ionos.player.ui.PlayerScreenEvent.LaunchStreamFileIntent
import com.ionos.player.ui.PlayerScreenEvent.ShowFileActions
import com.ionos.player.ui.PlayerScreenEvent.ShowFileDetails
import com.ionos.player.ui.PlayerScreenEvent.ShowFileExportStartedMessage
import com.ionos.player.ui.PlayerScreenEvent.ShowRemoveFileDialog
import com.ionos.player.ui.PlayerScreenEvent.ShowShareFileDialog
import com.ionos.player.ui.audio.AudioPlayerView
import com.ionos.player.ui.video.VideoPlayerView
import com.ionos.player.util.SystemVersion
import com.ionos.player.util.isPictureInPictureAllowed
import com.nextcloud.client.di.Injectable
import com.nextcloud.ui.fileactions.FileAction
import com.nextcloud.ui.fileactions.FileActionsBottomSheet
import com.owncloud.android.R
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.activity.FileActivity
import com.owncloud.android.ui.activity.FileDisplayActivity
import com.owncloud.android.ui.dialog.ConfirmationDialogFragment
import com.owncloud.android.ui.dialog.RemoveFilesDialogFragment
import com.owncloud.android.utils.DisplayUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PlayerActivity : FileActivity(), Injectable {

    companion object {
        private const val PLAYBACK_FILE_TYPE: String = "PLAYBACK_FILE_TYPE"

        fun createIntent(context: Context, playbackFileType: PlaybackFileType): Intent {
            return Intent(context, PlayerActivity::class.java).apply {
                putExtra(PLAYBACK_FILE_TYPE, playbackFileType)
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            }
        }
    }

    @Inject
    lateinit var playbackModel: PlaybackModel

    @Inject
    lateinit var viewModelFactory: PlayerViewModel.Factory

    private val viewModel by viewModels<PlayerViewModel> { viewModelFactory }

    private lateinit var playbackFileType: PlaybackFileType

    private lateinit var playerView: PlayerView

    private val pipAspectRatio = Rational(16, 9)

    private var onBackPressedCallback: OnBackPressedCallback? = null

    override fun isDefaultWindowInsetsHandlingEnabled(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        playbackFileType = intent.getPlaybackFileType()
        createPlayerView()

        viewModel.eventFlow
            .flowWithLifecycle(lifecycle)
            .onEach { handleEvent(it) }
            .launchIn(lifecycleScope)

        if (isPictureInPictureAllowed()) {
            val isVideoPlayback = playbackFileType == PlaybackFileType.VIDEO
            onBackPressedCallback = onBackPressedDispatcher.addCallback(this, enabled = isVideoPlayback) {
                switchToPictureInPictureMode()
            }
        }

        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        playbackFileType = intent.getPlaybackFileType()
        recreatePlayerView()
        onBackPressedCallback?.isEnabled = canUsePictureInPictureMode()
    }

    private fun createPlayerView() {
        playerView = when (playbackFileType) {
            PlaybackFileType.AUDIO -> AudioPlayerView(this)
            PlaybackFileType.VIDEO -> VideoPlayerView(this)
        }
        val moreButton = playerView.findViewById<View>(R.id.more)
        moreButton.setOnClickListener { viewModel.onMoreButtonClick() }
        setContentView(playerView)
    }

    private fun recreatePlayerView() {
        playerView.onStop()
        createPlayerView()
        playerView.onStart()
    }

    @Suppress("DEPRECATION")
    private fun Intent.getPlaybackFileType(): PlaybackFileType {
        val playbackFileType = if (SystemVersion.greaterOrEqualToTiramisu()) {
            getSerializableExtra(PLAYBACK_FILE_TYPE, PlaybackFileType::class.java)
        } else {
            getSerializableExtra(PLAYBACK_FILE_TYPE) as PlaybackFileType?
        }
        return playbackFileType ?: throw IllegalStateException("Playback file type was not defined")
    }

    override fun onStart() {
        super.onStart()
        playerView.onStart()
    }

    override fun onStop() {
        super.onStop()
        playerView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing && playbackFileType == PlaybackFileType.VIDEO) {
            playbackModel.release()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreatePlayerView()
        if (isInPictureInPictureMode) {
            (playerView as? VideoPlayerView)?.hideControls()
        } else {
            (playerView as? VideoPlayerView)?.showControls()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (canUsePictureInPictureMode()) {
            switchToPictureInPictureMode()
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (!isInPictureInPictureMode && lifecycle.currentState == Lifecycle.State.CREATED) {
            finish() // Finish the activity if the user closes the PIP window
        }
    }

    private fun canUsePictureInPictureMode(): Boolean {
        return playbackFileType == PlaybackFileType.VIDEO && isPictureInPictureAllowed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun switchToPictureInPictureMode() {
        val params = createPictureInPictureParams()
        enterPictureInPictureMode(params)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPictureInPictureParams(): PictureInPictureParams {
        return PictureInPictureParams.Builder().let {
            it.setAspectRatio(pipAspectRatio)
            getSourceRectHint().let(it::setSourceRectHint)
            it.build()
        }
    }

    private fun getSourceRectHint(): Rect? {
        val containerRect = Rect()
        playerView.getGlobalVisibleRect(containerRect)
        val sourceHeightHint = (containerRect.width() / pipAspectRatio.toFloat()).toInt()
        return Rect(
            containerRect.left,
            containerRect.top + (containerRect.height() - sourceHeightHint) / 2,
            containerRect.right,
            containerRect.top + (containerRect.height() + sourceHeightHint) / 2,
        )
    }

    private fun handleEvent(event: PlayerScreenEvent) {
        when (event) {
            is ShowFileActions -> showFileActions(event.file, event.actionIds)
            is ShowFileDetails -> showFileDetails(event.file)
            is ShowFileExportStartedMessage -> showFileExportStartedMessage()
            is ShowShareFileDialog -> fileOperationsHelper.sendShareFile(event.file)
            is ShowRemoveFileDialog -> showRemoveFileDialog(event.file)
            is LaunchOpenFileIntent -> fileOperationsHelper.openFile(event.file)
            is LaunchStreamFileIntent -> fileOperationsHelper.streamMediaFile(event.file)
        }
    }

    private fun showFileActions(file: OCFile, actionIds: List<Int>) {
        val actionsToHide = FileAction.SORTED_VALUES.map(FileAction::id).filter { it !in actionIds }
        FileActionsBottomSheet.newInstance(file, false, actionsToHide)
            .setResultListener(supportFragmentManager, this) { viewModel.onFileActionChosen(file, it) }
            .show(supportFragmentManager, "actions")
    }

    private fun showFileDetails(file: OCFile) {
        val intent = Intent(this, FileDisplayActivity::class.java).apply {
            action = FileDisplayActivity.ACTION_DETAILS
            putExtra(EXTRA_FILE, file)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        finish()
    }

    private fun showFileExportStartedMessage() {
        val message = resources.getQuantityString(R.plurals.export_start, 1, 1)
        DisplayUtils.showSnackMessage(playerView, message)
    }

    private fun showRemoveFileDialog(file: OCFile) {
        RemoveFilesDialogFragment.newInstance(file)
            .show(supportFragmentManager, ConfirmationDialogFragment.FTAG_CONFIRMATION)
    }
}
