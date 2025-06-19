/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

data class PlaybackFiles(
    val list: List<PlaybackFile>,
    val comparator: PlaybackFilesComparator
)
