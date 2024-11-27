/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
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
