/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.interfaces;

import android.view.SurfaceHolder;

import androidx.annotation.Nullable;

/**
 * User: zuzik
 * Date: 8/14/16
 */
public interface VideoViewSetter {
	void setVideoView(@Nullable SurfaceHolder holder);
}
