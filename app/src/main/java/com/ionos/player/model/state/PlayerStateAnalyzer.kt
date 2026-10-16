/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state

class PlayerStateAnalyzer(
    private val state: PlayerState,
) {
    companion object {
        private val ALLOWED_PLAY_STATES = setOf(PlayerState.IDLE, PlayerState.PAUSED, PlayerState.COMPLETED)
        private val ALLOWED_PAUSE_STATES = setOf(PlayerState.PLAYING)
        private val ALLOWED_STOP_STATES = setOf(PlayerState.PLAYING, PlayerState.PAUSED, PlayerState.COMPLETED)
    }

    fun playAvailable(): Boolean {
        return ALLOWED_PLAY_STATES.contains(state)
    }

    fun pauseAvailable(): Boolean {
        return ALLOWED_PAUSE_STATES.contains(state)
    }

    fun stopAvailable(): Boolean {
        return ALLOWED_STOP_STATES.contains(state)
    }
}
