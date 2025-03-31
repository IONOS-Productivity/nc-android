/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.scanbot.exception;

import java.io.IOException;


public class NoFreeLocalSpaceException extends IOException {
	public NoFreeLocalSpaceException() {
		this("NoFreeSpaceException");
	}

	public NoFreeLocalSpaceException(String message) {
		super(message);
	}
}
