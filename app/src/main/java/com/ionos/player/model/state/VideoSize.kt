/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state

import java.io.Serializable

data class VideoSize(
    val width: Int,
    val height: Int,
) : Serializable
