/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.error_strategy

import com.ionos.player.model.state.PlaybackState
import java.io.Serializable

interface PlaybackErrorStrategy : Serializable {
    fun switchToNextSource(error: Throwable, state: PlaybackState): Boolean
}
