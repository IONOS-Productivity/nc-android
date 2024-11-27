/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.image_loading

import com.ionos.scanbot.image_loader.ImageLoader
import com.ionos.scanbot.image_loader.ImageRequestBuilder
import java.io.File
import javax.inject.Inject

class ImageLoaderImpl @Inject constructor(
) : ImageLoader {

    override fun load(file: File): ImageRequestBuilder {
        return ImageRequestBuilderImpl(file)
    }

}