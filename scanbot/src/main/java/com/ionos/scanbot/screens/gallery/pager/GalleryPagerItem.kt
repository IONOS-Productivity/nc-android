/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.screens.gallery.pager

import android.widget.ImageView
import com.ionos.scanbot.entity.Picture

internal class GalleryPagerItem internal constructor(
	val picture: Picture,
	val imageView: ImageView,
)
