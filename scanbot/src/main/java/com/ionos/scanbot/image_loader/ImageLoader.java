
/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.image_loader;

import java.io.File;

import androidx.annotation.NonNull;

public interface ImageLoader {

	ImageRequestBuilder load(@NonNull File file);

}
