package com.ionos.player.model

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ionos.player.media3.resumption.PlaybackResumptionRepository
import com.ionos.player.ui.PlayerActivity
import com.nextcloud.client.logger.Logger
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.fragment.SearchType
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PlayerLauncher @Inject constructor(
	private val playbackResumptionRepository: PlaybackResumptionRepository,
	private val playbackFilesRepository: PlaybackFilesRepository,
	private val playbackModel: PlaybackModel,
	private val logger: Logger,
) {
	private var currentLaunchJob: Job? = null

	fun launch(activity: AppCompatActivity, file: OCFile, searchType: SearchType?) {
		currentLaunchJob?.cancel()
		currentLaunchJob = activity.lifecycleScope.launch {
			try {
				val fileType = file.getPlaybackFileType()
				playbackResumptionRepository.saveConfig(file.localId.toString(), file.parentId, fileType, searchType)

				val currentPlaybackFile = file.toPlaybackFile()
				playbackModel.start()
				playbackModel.setFiles(listOf(currentPlaybackFile))
				playbackModel.switchToFile(currentPlaybackFile)
				playbackModel.play()

				val intent = createIntent(activity, fileType)
				activity.startActivity(intent)

				val playbackFiles = playbackFilesRepository.load(file.parentId, fileType, searchType)
				playbackModel.state.ifPresent {
					playbackModel.setFiles(playbackFiles)
				}
			} catch (t: Throwable) {
				if (t is CancellationException) throw t
				logger.e(PlayerLauncher::class.java.simpleName, "Error launching player", t)
			}
		}
	}

	private fun createIntent(context: Context, fileType: PlaybackFileType): Intent = when (fileType) {
		PlaybackFileType.AUDIO -> PlayerActivity.createAudioPlayerIntent(context)
		PlaybackFileType.VIDEO -> PlayerActivity.createVideoPlayerIntent(context)
	}

	private fun OCFile.getPlaybackFileType(): PlaybackFileType {
		return PlaybackFileType.entries
			.firstOrNull { mimeType.startsWith(it.value, ignoreCase = true) }
			?: throw IllegalArgumentException("Unsupported file type: $mimeType")
	}
}
