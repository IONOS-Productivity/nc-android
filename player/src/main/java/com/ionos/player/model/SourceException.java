/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model;

/**
 * User: shevchuk anton
 * Date: 01/26/17
 */
public class SourceException extends Exception {
	public final int errorCode;

	public SourceException() {
		this(0);
	}

	public SourceException(int errorCode) {
		super("Source not found");
		this.errorCode = errorCode;
	}
}
