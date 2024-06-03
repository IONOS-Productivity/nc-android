package com.ionos.scanbot.screens.save.use_case

import com.ionos.domain.upload.targetprovider.UploadTarget
import com.ionos.domain.utils.FileUtils
import com.ionos.scanbot.repository.RepositoryFacade
import com.ionos.scanbot.screens.save.SaveScreen.FileType
import com.ionos.scanbot.exception.InvalidFileNameException
import com.ionos.scanbot.exception.OverwriteFilesException
import com.ionos.scanbot.screens.save.use_case.name.GetFileNames
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

internal class ValidateFilesForUpload @Inject constructor(
	private val getFileNames: GetFileNames,
	private val repositoryFacade: RepositoryFacade,
    // TODO alk
	// private val remoteFilePathOverwritePredicate: RemoteFilePathOverwritePredicate,
) {

	operator fun invoke(uploadTarget: UploadTarget, baseFileName: String, fileType: FileType): Completable {
		return validateBaseFileName(baseFileName)
			.andThen(checkForOverwrite(uploadTarget, baseFileName, fileType))
	}

	private fun validateBaseFileName(baseFileName: String) = Completable.fromCallable {
		if (!FileUtils.isValidName(baseFileName)) {
			throw InvalidFileNameException(baseFileName)
		}
	}

	private fun checkForOverwrite(uploadTarget: UploadTarget, baseFileName: String, fileType: FileType): Completable {
		return Single
			.fromCallable { repositoryFacade.readAll().size }
			.map { count -> getFileNames(baseFileName, fileType, count) }
			.flattenAsObservable { fileNames -> fileNames }
			.map { fileName -> File(uploadTarget.remotePath, fileName).path }
			.flatMapMaybe { remoteFilePath -> overwritePathFilter(remoteFilePath) }
			.toList()
			.flatMapCompletable { overwritePaths -> completeIfEmptyOrError(overwritePaths) }
	}

	private fun overwritePathFilter(remoteFilePath: String): Maybe<String> {
	return Maybe.empty()
    // return remoteFilePathOverwritePredicate
		// 	.satisfied(remoteFilePath)
		// 	.flatMapMaybe { overwrite -> if (overwrite) Maybe.just(remoteFilePath) else Maybe.empty() }
	}

	private fun completeIfEmptyOrError(overwritePaths: List<String>): Completable {
		return if (overwritePaths.isEmpty()) {
			Completable.complete()
		} else {
			Completable.error(OverwriteFilesException(overwritePaths))
		}
	}
}
