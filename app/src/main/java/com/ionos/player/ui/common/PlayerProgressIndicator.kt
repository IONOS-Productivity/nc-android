package com.ionos.player.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.ionos.player.model.PlaybackFile
import com.ionos.player.model.PlaybackModel
import com.ionos.player.model.state.PlaybackItemState
import com.ionos.player.model.state.PlaybackState
import com.ionos.player.model.state.PlayerState
import com.ionos.player.model.toPlaybackFile
import com.owncloud.android.datamodel.OCFile
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

class PlayerProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
) : LinearProgressIndicator(context, attrs, defStyleAttr) {

    @Inject
    lateinit var playbackModel: PlaybackModel

    private var playbackFile: PlaybackFile? = null

    init {
        indicatorTrackGapSize = 0
        trackStopIndicatorSize = 0
        if (!isInEditMode) {
            (context.applicationContext as HasAndroidInjector).androidInjector().inject(this)
        }
    }

    fun onBind(file: OCFile) {
        playbackFile = file.toPlaybackFile()
        val itemState = playbackModel.state.flatMap(PlaybackState::currentItemState).getOrNull()
        render(itemState)
        playbackModel.addListener(playbackModelListener)
    }

    fun onRecycled() {
        playbackModel.removeListener(playbackModelListener)
        visibility = GONE
        playbackFile = null
    }

    private fun render(itemState: PlaybackItemState?) {
        if (itemState != null && itemState.playerState != PlayerState.COMPLETED && itemState.file == playbackFile) {
            max = itemState.maxTimeInMilliseconds
            progress = itemState.currentTimeInMilliseconds
            visibility = VISIBLE
        } else {
            visibility = GONE
        }
    }

    private val playbackModelListener = object : PlaybackModel.Listener {

        override fun onUpdate(state: PlaybackState) {
            val itemState = state.currentItemState.getOrNull()
            render(itemState)
        }

        override fun onError(error: Throwable) {
        }

        override fun onFilesChanged(originalFiles: List<PlaybackFile>, currentFiles: List<PlaybackFile>) {
        }
    }
}
