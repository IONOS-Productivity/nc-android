/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

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
) : LinearProgressIndicator(context, attrs, defStyleAttr),
    PlaybackModel.Listener {

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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        renderCurrentState()
        playbackModel.addListener(this)
    }

    override fun onDetachedFromWindow() {
        playbackModel.removeListener(this)
        visibility = GONE
        super.onDetachedFromWindow()
    }

    override fun onPlaybackUpdate(state: PlaybackState) {
        val itemState = state.currentItemState
        render(itemState)
    }

    override fun onPlaybackError(error: Throwable) {
    }

    fun setFile(file: OCFile) {
        playbackFile = file.toPlaybackFile()
        renderCurrentState()
    }

    private fun renderCurrentState() {
        val itemState = playbackModel.state.getOrNull()?.currentItemState
        render(itemState)
    }

    private fun render(itemState: PlaybackItemState?) {
        if (itemState != null && itemState.playerState != PlayerState.COMPLETED && itemState.file.id == playbackFile?.id) {
            max = itemState.maxTimeInMilliseconds
            progress = itemState.currentTimeInMilliseconds
            visibility = VISIBLE
        } else {
            visibility = GONE
        }
    }
}
