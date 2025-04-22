/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.exception;

/**
 * User: zuzik
 * Date: 5/29/16
 */
public class AudioFocusLostException extends Exception {
	public AudioFocusLostException() {
		super("Audio focus lost");
	}
}
