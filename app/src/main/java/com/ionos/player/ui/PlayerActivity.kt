package com.ionos.player.ui

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import com.owncloud.android.ui.dialog.ConfirmationDialogFragment
import com.owncloud.android.ui.dialog.RemoveFilesDialogFragment
import com.owncloud.android.utils.DisplayUtils
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
    lateinit var playbackModel: PlaybackModel

    @Inject
    lateinit var viewModelFactory: PlayerViewModel.Factory
    private val viewModel by viewModels<PlayerViewModel> { viewModelFactory }

    private val surfaceInvalidator = SurfaceInvalidator()
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerView = createPlayerView()
        setContentView(playerView)

        val moreButton = findViewById<View>(R.id.more)
        moreButton.setOnClickListener { viewModel.onMoreButtonClick() }

        viewModel.eventFlow
            .flowWithLifecycle(lifecycle)
            .onEach { handleEvent(it) }
            .launchIn(lifecycleScope)

        if (getPlaybackFileType() == PlaybackFileType.VIDEO) {
            onBackPressedDispatcher.addCallback(this) {
                tryToMinimize()
            }
        }
    }

    private fun createPlayerView(): PlayerView = when (getPlaybackFileType()) {
        PlaybackFileType.AUDIO -> AudioPlayerView(this)
        PlaybackFileType.VIDEO -> VideoPlayerView(this)
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
        if (getPlaybackFileType() == PlaybackFileType.VIDEO) {
            playbackModel.release()
        }
        super.onStop()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        playerView.onStop()
        playerView = createPlayerView()
        if (isInPictureInPictureMode) {
            (playerView as? VideoPlayerView)?.hideControls()
        }
        setContentView(playerView)
        playerView.onStart()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        tryToMinimize()
    }

    private fun tryToMinimize() {
        val isFeatureAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        if (isFeatureAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            minimize()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun minimize() {
        val params = PictureInPictureParams.Builder()
            .build()
        enterPictureInPictureMode(params)
    }

    override fun onPlayerViewClose() {
        finish()
    }

    override fun getSurfaceInvalidator(): SurfaceInvalidator {
        return surfaceInvalidator
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
