/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

import javax.inject.Inject

class RemoveFilesFromPlayerQueue @Inject constructor(
	private val model: PlaybackModel,
) {

	operator fun invoke(deletedFiles: List<PlaybackFile>) {
		val state = model.state.get()
		if (!state.currentItemState.isPresent) return

		val currentPlaybackItemState = state.currentItemState.get()
		val currentFileInQueue = currentPlaybackItemState.file
		val currentFiles = state.currentFiles.toMutableList()

		val deletedExceptCurrent = deletedFiles.toMutableList()
		val containedCurrent = deletedExceptCurrent.remove(currentFileInQueue)

		if (containedCurrent) {
			currentFiles.removeAll(deletedExceptCurrent)
			if (currentFiles.size > 1) {
				val indexOfFileToDelete = currentFiles.indexOf(currentFileInQueue)
				val indexOfNextFile =
					if (indexOfFileToDelete > 0) indexOfFileToDelete - 1
					else 1
				val nextFile = currentFiles[indexOfNextFile]
				currentFiles.removeAt(indexOfFileToDelete)

				model.switchToFile(nextFile)
				model.setFiles(currentFiles)
			} else model.release()
		} else {
			val changed = currentFiles.removeAll(deletedFiles)
			if (changed) {
				if (currentFiles.isEmpty()) model.release()
				else {
					model.setFiles(currentFiles)
				}
			}
		}
	}

}