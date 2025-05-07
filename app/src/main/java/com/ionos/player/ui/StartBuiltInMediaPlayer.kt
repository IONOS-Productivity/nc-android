package com.ionos.player.ui

import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import com.ionos.player.model.MultiplePlayer
import com.ionos.player.model.NeighborFilesTypes
import com.ionos.player.model.OpenFileConfig
import com.ionos.player.model.PlayerFileInfo
import com.ionos.player.model.getNeighborFilesTypes
import com.ionos.player.model.release_strategy.DoNotReleaseIfExistsSourceInfoReleaseStrategy
import com.ionos.player.model.toPlayerFileInfo
import com.owncloud.android.datamodel.OCFile
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@AssistedFactory
interface StartBuiltInMediaPlayerFactory {
	fun create(
		input: OpenFileConfig,
		options: ActivityOptionsCompat,
		activityLauncher: ActivityResultLauncher<MediaPlayerResultContract.Input>?,
	): StartBuiltInMediaPlayer
}

class StartBuiltInMediaPlayer @AssistedInject constructor(
	@Assisted private val input: OpenFileConfig,
	@Assisted private val options: ActivityOptionsCompat,
	@Assisted private val activityLauncher: ActivityResultLauncher<MediaPlayerResultContract.Input>?,
    private val playerModel: MultiplePlayer.Model<PlayerFileInfo>,
) {

	private val typeError = IllegalArgumentException("Supports only audion or video")

	fun invoke(): Completable {
		val type = input.fileInfo.getNeighborFilesTypes()

		if (type != NeighborFilesTypes.AUDIO && type != NeighborFilesTypes.VIDEO)
			return Completable.error(typeError)

		return Completable
			.create { playerModel.start(it::onComplete, it::onError) }
            .andThen(Completable.fromAction { launchPlayer(input.fileInfo, type) })
			.andThen(createObservable(input, type))
			.map { it.map(OCFile::toPlayerFileInfo) }
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext { sourceInfos ->
				playerModel.state.ifPresent {
					playerModel.setSourceInfos(
						sourceInfos,
						DoNotReleaseIfExistsSourceInfoReleaseStrategy()
					)
				}
			}
			.ignoreElements()
	}

	@Throws(IllegalStateException::class)
	private fun launchPlayer(
		fileInfo: OCFile,
		// sourceMode: FileSourceMode,
		filesTypes: NeighborFilesTypes,
	) {
		val playerInfo = fileInfo.toPlayerFileInfo()
		playerModel.setSourceInfos(
			listOf(playerInfo),
			DoNotReleaseIfExistsSourceInfoReleaseStrategy()
		)
		playerModel.switchToSourceInfo(playerInfo)
		playerModel.play()
		activityLauncher?.launch(
			MediaPlayerResultContract.Input(
				filesTypes,
				// sourceMode,
			),
			options
		)
	}

	private fun createObservable(
		input: OpenFileConfig,
		filesTypes: NeighborFilesTypes,
	): Observable<List<OCFile>> {
		return Observable.just(listOf(input.fileInfo))
			.subscribeOn(Schedulers.io())
	}

}