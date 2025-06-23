/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.video

import android.content.Context
import android.view.MotionEvent
import android.view.WindowInsets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ionos.player.ui.PlayerView
import com.owncloud.android.R
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoPlayerView(context: Context) : PlayerView(context) {

    companion object {
        private const val HIDE_CONTROLS_DELAY = 5000L
    }

    override val layoutRes get() = R.layout.player_video_view

    override val fragmentFactory get() = VideoFileFragmentFactory()

    private var hideControlsTimerJob: Job? = null

    init {
        if (!isInEditMode) {
            startHideControlsTimer()
        }
    }

    override fun inject(context: Context) {
        (context.applicationContext as HasAndroidInjector).androidInjector().inject(this)
    }

    override fun onApplyWindowInsets(windowInsets: WindowInsets): WindowInsets? {
        val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
        val insets = windowInsetsCompat.getInsets(Type.systemBars() or Type.displayCutout())

        topBar.setPadding(insets.left, insets.top, insets.right, 0)
        playerControlView.setPadding(insets.left, 0, insets.right, insets.bottom)

        windowWrapper.setupStatusBar(R.color.player_transparent_dark, false)
        windowWrapper.setupNavigationBar(R.color.player_transparent_dark, false)

        return WindowInsetsCompat.CONSUMED.toWindowInsets()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val isTouchOutsideControls = event.y < playerControlView.y && event.y > topBar.height
            when {
                !playerControlView.isVisible -> showControls()
                isTouchOutsideControls -> hideControls()
                else -> startHideControlsTimer()
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun showControls() {
        windowWrapper.showSystemBars()
        topBar.visibility = VISIBLE
        playerControlView.visibility = VISIBLE
        startHideControlsTimer()
    }

    fun hideControls() {
        windowWrapper.hideSystemBars()
        topBar.visibility = GONE
        playerControlView.visibility = GONE
    }

    private fun startHideControlsTimer() {
        hideControlsTimerJob?.cancel()
        hideControlsTimerJob = activity.lifecycleScope.launch {
            delay(HIDE_CONTROLS_DELAY)
            hideControls()
        }
    }
}
