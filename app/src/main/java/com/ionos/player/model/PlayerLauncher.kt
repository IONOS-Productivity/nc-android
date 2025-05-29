package com.ionos.player.model

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ionos.player.media3.resumption.PlaybackResumptionConfigStore
import com.ionos.player.ui.PlayerActivity
import com.nextcloud.client.logger.Logger
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.fragment.SearchType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

class PlayerLauncher @Inject constructor(
	private val playbackResumptionConfigStore: PlaybackResumptionConfigStore,
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
				playbackResumptionConfigStore.saveConfig(file.localId.toString(), file.parentId, fileType, searchType)

				val currentPlaybackFile = file.toPlaybackFile()

				val playbackFilesFlow = playbackFilesRepository.observe(file.parentId, fileType, searchType)
					.onStart { emit(PlaybackFiles(listOf(currentPlaybackFile), PlaybackFilesComparator.NONE)) }

				playbackModel.start()
				playbackModel.setFilesFlow(playbackFilesFlow)
				playbackModel.switchToFile(currentPlaybackFile)
				playbackModel.play()

				val intent = PlayerActivity.createIntent(activity, fileType)
				activity.startActivity(intent)
			} catch (t: Throwable) {
				if (t is CancellationException) throw t
				logger.e(PlayerLauncher::class.java.simpleName, "Error launching player", t)
			}
		}
	}

	private fun OCFile.getPlaybackFileType(): PlaybackFileType {
		return PlaybackFileType.entries
			.firstOrNull { mimeType.startsWith(it.value, ignoreCase = true) }
			?: throw IllegalArgumentException("Unsupported file type: $mimeType")
	}
}
