package com.strato.hidrive.player

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.player_mode.PlayerMode
import com.strato.hidrive.player.player_source_release_strategy.DoNotReleaseIfExistsSourceInfoReleaseStrategy
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer
import javax.inject.Inject

class RemoveFilesFromPlayerQueue @Inject constructor(
	private val model: MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode>,
) {

	operator fun invoke(deletedFiles: List<PlayerFileInfo>) {
		val state = model.state.get()
		if (!state.currentPlaybackState.isPresent) return

		val currentPlaybackState = state.currentPlaybackState.get()
		val currentFileInQueue = currentPlaybackState.sourceInfo
		val sourceInfos = state.currentSourceInfos.toMutableList()

		val deletedExceptCurrent = deletedFiles.toMutableList()
		val containedCurrent = deletedExceptCurrent.remove(currentFileInQueue)

		if (containedCurrent) {
			sourceInfos.removeAll(deletedExceptCurrent)
			if (sourceInfos.size > 1) {
				val indexOfSourceInfoToDelete = sourceInfos.indexOf(currentFileInQueue)
				val indexOfNextSourceInfo =
					if (indexOfSourceInfoToDelete > 0) indexOfSourceInfoToDelete - 1
					else 1
				val nextSourceInfo = sourceInfos[indexOfNextSourceInfo]
				sourceInfos.removeAt(indexOfSourceInfoToDelete)

				model.switchToSourceInfo(nextSourceInfo)
				model.setSourceInfos(sourceInfos, DoNotReleaseIfExistsSourceInfoReleaseStrategy())
			} else model.release()
		} else {
			val changed = sourceInfos.removeAll(deletedFiles)
			if (changed) {
				if (sourceInfos.isEmpty()) model.release()
				else {
					model.setSourceInfos(
						sourceInfos,
						DoNotReleaseIfExistsSourceInfoReleaseStrategy()
					)
				}
			}
		}
	}

}