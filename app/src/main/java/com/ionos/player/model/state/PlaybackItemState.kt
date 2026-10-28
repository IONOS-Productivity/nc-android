/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state

import com.ionos.player.model.PlaybackFile
import java.io.Serializable

data class PlaybackItemState(
    val file: PlaybackFile,
    val playerState: PlayerState,
    val metadata: PlaybackItemMetadata?,
    val videoSize: VideoSize?,
    val currentTimeInMilliseconds: Int,
    val maxTimeInMilliseconds: Int,
) : Serializable
