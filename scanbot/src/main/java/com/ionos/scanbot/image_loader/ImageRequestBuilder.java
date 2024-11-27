/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.image_loader;

import android.widget.ImageView;

import androidx.annotation.NonNull;

public interface ImageRequestBuilder {

    ImageRequestBuilder options(@NonNull ImageLoaderOptions options);

    void into(@NonNull ImageView target);

}
