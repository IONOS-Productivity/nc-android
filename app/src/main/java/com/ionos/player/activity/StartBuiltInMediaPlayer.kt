package com.ionos.player.activity

import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import com.ionos.player.NeighborFilesTypes
import com.ionos.player.activity.contract.MediaPlayerResultContract
import com.ionos.player.cache.PlayerSourceInfoCache
import com.ionos.player.transformation.oc_file.OCFileToNeighborFilesTypesTransformation
import com.ionos.player.transformation.oc_file.OCFileToPlayerFileInfoTransformation
import com.owncloud.android.datamodel.OCFile
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.player_mode.PlayerMode
import com.ionos.player.player_source_release_strategy.DoNotReleaseIfExistsSourceInfoReleaseStrategy
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer
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
    private val playerModel: MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode>,
    private val sourceInfoCache: PlayerSourceInfoCache,
    private val toNeighborFilesTypes: OCFileToNeighborFilesTypesTransformation,
	private val toPlayerFileInfo: OCFileToPlayerFileInfoTransformation,
) {

	private val typeError = IllegalArgumentException("Supports only audion or video")

	fun invoke(): Completable {
		val type = toNeighborFilesTypes.transform(input.fileInfo)

		if (type != NeighborFilesTypes.AUDIO && type != NeighborFilesTypes.VIDEO)
			return Completable.error(typeError)

		return Completable
			.create { playerModel.start(PlayerMode.Mode.REGULAR, it::onComplete, it::onError) }
            .doOnComplete(sourceInfoCache::clear)
            .andThen(Completable.fromAction { launchPlayer(input.fileInfo, type) })
			.andThen(createObservable(input, type))
			.map { it.map(toPlayerFileInfo::transform) }
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
		val playerInfo = toPlayerFileInfo.transform(fileInfo)
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