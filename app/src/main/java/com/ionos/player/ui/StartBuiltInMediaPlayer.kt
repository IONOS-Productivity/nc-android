package com.ionos.player.ui

import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import com.ionos.player.model.NeighborFilesType
import com.ionos.player.model.OpenFileConfig
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.getNeighborFilesType
import com.ionos.player.model.toPlaybackFile
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
    private val playerModel: PlaybackModel,
) {

	private val typeError = IllegalArgumentException("Supports only audion or video")

	fun invoke(): Completable {
		val type = input.file.getNeighborFilesType()

		if (type != NeighborFilesType.AUDIO && type != NeighborFilesType.VIDEO)
			return Completable.error(typeError)

		return Completable
			.create { playerModel.start(it::onComplete, it::onError) }
            .andThen(Completable.fromAction { launchPlayer(input.file, type) })
			.andThen(createObservable(input, type))
			.map { it.map(OCFile::toPlaybackFile) }
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext { sourceInfos ->
				playerModel.state.ifPresent {
					playerModel.setFiles(sourceInfos)
				}
			}
			.ignoreElements()
	}

	@Throws(IllegalStateException::class)
	private fun launchPlayer(
		file: OCFile,
		// sourceMode: FileSourceMode,
		filesType: NeighborFilesType,
	) {
		val playbackFile = file.toPlaybackFile()
		playerModel.setFiles(listOf(playbackFile))
		playerModel.switchToFile(playbackFile)
		playerModel.play()
		activityLauncher?.launch(
			MediaPlayerResultContract.Input(
				filesType,
				// sourceMode,
			),
			options
		)
	}

	private fun createObservable(
		input: OpenFileConfig,
		filesType: NeighborFilesType,
	): Observable<List<OCFile>> {
		return Observable.just(listOf(input.file))
			.subscribeOn(Schedulers.io())
	}

}