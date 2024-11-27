/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.license.oath.security.key;


public class AesGcmKey {
	public final byte[] key;
	public final byte[] iv;

	public AesGcmKey(byte[] key, byte[] iv) {
		this.key = key;
		this.iv = iv;
	}
}
