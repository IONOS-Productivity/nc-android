/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state

import com.ionos.player.model.PlaybackFile
import java.io.Serializable

class PlaybackState(
    val currentFiles: List<PlaybackFile>,
    val currentItemState: PlaybackItemState?,
    val repeatMode: RepeatMode,
    val shuffle: Boolean,
) : Serializable
