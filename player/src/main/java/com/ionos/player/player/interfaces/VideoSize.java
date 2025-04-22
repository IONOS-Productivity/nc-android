/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.interfaces;

/**
 * Created by AntonShevchuk on 12.01.2017.
 */

public final class VideoSize {
	private final int height;
	private final int width;

	public VideoSize(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VideoSize) {
			VideoSize videoSize = (VideoSize) obj;
			return this.width == videoSize.width && this.height == videoSize.height;
		} else {
			return false;
		}
	}
}
